(ns quil-tests.helpers
  (:require [quil.core :as q]))


(defmacro with-matrix
  [& body]
  `(do
     (q/push-matrix)
     ~@body
     (q/pop-matrix)))


(defmacro with-style
  [styles & body]
  (let [styles# `(run!
                  (fn [x#]
                    (apply
                     (resolve (symbol "quil.core" (name (first x#))))
                     (rest x#)))
                  ~styles)]
    `(with-matrix
       ~styles#
       ~@body)))


(def samples-per-frame 8)

(def num-frames 360)

(def shutter-angle 0.35)


(defmacro with-frames
  "Creates a variable called ranged-frame to be used at the
  body scope"
  [frame-count & body]
  `(doseq [sa# (range 0 samples-per-frame)]
     (let [~'ranged-frame (q/map-range
                           (+ (dec ~frame-count)
                              (-> sa# (* shutter-angle) (/ samples-per-frame)))
                                       0
                                       num-frames
                                       0
                                       1)]
       ~@body)))
