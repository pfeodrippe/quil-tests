(ns quil-tests.cortina
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-tests.helpers :refer :all]))

;; Author: Paulo Feodrippe

(def N 47)


(defn setup []
  (q/frame-rate 30)
  (q/smooth 8)
  (q/no-stroke)
  {})


(def styles
  {})


(defn update-state [state]
  {})


(defn sined-ellipse [ranged-frame i j amp size]
  (q/ellipse (-> ranged-frame
                 (* amp)
                 (+ (* 0.2 (+ 0.1 j)))
                 q/sin
                 (* 0.1 (+ 0.1 i) (+ 0.5 j)))
             (-> ranged-frame
                 (* amp)
                 (+ (* 0.2 (+ 0.1 i)))
                 q/cos
                 (* 0.1 (+ 0.1 i) (+ 0.1 j)))
             size
             size))


(defn draw-state [state]
  (with-frames (q/frame-count) 1 440 0.35 276 false
    (q/background 50)
    (with-matrix
      (q/translate 10 10)
      (doseq [i (range 0 N)
              j (range 0 N)]
        (with-style (:circle styles)
          (q/translate (* i 10)
                       (* j 10))
          (do
            (with-style [[:fill 50 100 100]]
              (sined-ellipse ranged-frame i j 10 8))
            (with-style [[:fill 200 100 0]]
              (sined-ellipse ranged-frame i j 20 6))
            (with-style [[:fill 150 150 0]]
              (sined-ellipse ranged-frame i j 40 4))))))))


(q/defsketch Cortina
  :title "Paulo Feodrippe - Cortina"
  :size [700  700]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
