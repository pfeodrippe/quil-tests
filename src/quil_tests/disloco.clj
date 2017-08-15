(ns quil-tests.disloco
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-tests.helpers :refer :all]))

;; Author: Paulo Feodrippe

(def N 47)


(defn setup []
  (q/frame-rate 30)
  (q/smooth 8)
  (q/no-stroke)
  (q/stroke 10)
  {})


(def styles
  {})


(defn update-state [state]
  {})


(defn wiggled-square [ranged-frame i j freq amp interval]
  (with-shape
    (q/translate (+ (- 235) (* i interval))
                 (+ (- 235) (* j interval)))
    (q/stroke (+ 200 (* 100 (q/sin (-> i (* 10 j)))))
                (+ 50 (* 20 (q/sin (-> (* 10 j)))))
                (+ 200 (* 20 (q/sin (-> j (* 10))))))
    (q/line 0
            0
            (+ amp
               (-> ranged-frame
                   (* freq)
                   (q/sin)
                   (* amp)))
            0)
    (q/line amp amp 0 (+ amp
                         (-> ranged-frame
                             (* freq)
                             (q/cos)
                             (* amp))))
    (q/line amp
            amp
            (+ amp
               (-> ranged-frame
                   (* freq)
                   (q/sin)
                   (* amp)))
            0)
    (q/line 0 0 0 (+ amp
                     (-> ranged-frame
                         (* freq)
                         (q/cos)
                         (* amp))))))


(defn draw-state [state]
  (with-frames (q/frame-count) 1 360 0.35 76 false
    (q/background 40)
    (with-matrix
      (q/no-fill)
      (q/stroke 100 200 200)
      (q/translate (/ (q/width) 2) (/ (q/height) 2))
      (doseq [i (range N)
              j (range N)]
        (wiggled-square (+ ranged-frame (+ (* j 0.003)
                                           (* i 0.002)))
                        i
                        j
                        30
                        4 10)))))


(q/defsketch Dislocó
  :title "Paulo Feodrippe - Dislocó"
  :size [500  500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
