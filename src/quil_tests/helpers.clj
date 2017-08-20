(ns quil-tests.helpers
  (:require [quil.core :as q]))


;; Some ideias taken from https://gist.github.com/beesandbombs


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
     (with-matrix
       ~@body)
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
       (q/push-style)
       ~styles#
       ~@body
       (q/pop-style))))


(defmacro with-frames
  "Creates a variable called ranged-frame to be used at the
  body scope"
  [frame-count samples-per-frame num-frames
   shutter-angle max-frames save? & body]
  `(if ~save?
     (do
       (doseq [sa# (range 0 ~samples-per-frame)]
         (let [~'ranged-frame (q/map-range
                               (+ (dec ~frame-count)
                                  (-> sa# (* ~shutter-angle) (/ ~samples-per-frame)))
                               0
                               ~num-frames
                               0
                               1)]
           ~@body))
       (q/save-frame "f###.gif")
       (when (or #_(>= ~frame-count ~num-frames)
                 (>= ~frame-count ~max-frames))
         (q/exit)))
     (let #_[~'ranged-frame (* 2
                             (/ (float (q/mouse-x))
                                (q/width)))]
          [~'ranged-frame (q/map-range (dec ~frame-count) 0 ~num-frames 0 1)]
       ~@body
       (with-style []
         (q/fill 150)
         (q/text (str ~frame-count "\n" ~'ranged-frame)
                 (- (q/width) 100)
                 20)))
     #_(let [~'ranged-frame (/ (float (q/mouse-x))
                             (q/width))]
       ~@body
       )))





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
