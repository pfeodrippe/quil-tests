(ns quil-tests.dots
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-tests.helpers :refer :all]))

;; example from https://twitter.com/beesandbombs
;; https://gist.github.com/anonymous/9f7e57ab4ec7d8073e545e36db1ecf64

(def N 12)


(defn setup []
  (q/frame-rate 30)
  (q/smooth 8)
  (q/fill 32)
  (q/no-stroke)
  {})


(def styles
  {:arc [[:stroke 32]
         [:no-fill]]
   :circle [[:fill 32]]})


(defn update-state [state]
  {})


(defn draw-state [state]
  (with-frames (q/frame-count) 8 360 0.35 false
    (q/background 250)
    (with-matrix
      (q/translate (/ (q/width) 2) 160)
      (doseq [i (range 0 N)]
        (let [l (q/map-range i -0.75 (dec N) 0 320)]
          (with-style (:arc styles)
            (q/arc 0 0 (* 2 l) (* 2 l) 0 q/PI))
          (with-style (:circle styles)
            (q/rotate (-> ranged-frame
                          (* (- N i))
                          (* q/TWO-PI)
                          q/sin
                          (* q/HALF-PI)))
            (q/translate 0 l)
            (q/ellipse 0 0 14 14)))))))


(q/defsketch dots
  :title "Bees and Bombs - Dots"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
