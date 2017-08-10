(ns quil-tests.helpers
  (:require [quil.core :as q]))


(defmacro with-matrix
  [& body]
  `(do
     (q/push-matrix)
     ~@body
     (q/pop-matrix)))


(defmacro with-shape
  [& body]
  `(do
     (q/begin-shape)
     ~@body
     (q/end-shape)))


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


(defmacro with-frames
  "Creates a variable called ranged-frame to be used at the
  body scope"
  [frame-count samples-per-frame num-frames shutter-angle & body]
  `(doseq [sa# (range 0 ~samples-per-frame)]
     (let [~'ranged-frame (q/map-range
                           (+ (dec ~frame-count)
                              (-> sa# (* ~shutter-angle) (/ ~samples-per-frame)))
                           0
                           ~num-frames
                           0
                           1)]
       ~@body
       (q/save-frame "f###.gif"))))


(defn ease
  ([p]
   (- (* 3 p p)
      (* 2 p p p)))
  ([p g]
   (if (< p 0.5)
     (* 0.5 (q/pow (* 2 p)
                   g))
     (* (- 1 0.5) (q/pow (* 2 (- 1 p))
                         g)))))
