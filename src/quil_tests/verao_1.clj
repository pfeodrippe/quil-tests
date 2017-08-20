(ns quil-tests.verao-1
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-tests.helpers :refer :all]))

;; Author: Paulo Feodrippe

(def N 500)


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


(defn burn [ranged-frame]
  (with-style [[:stroke
                (+ 30 (rand 0))
                (+ 30 (rand 0))
                (+ 30 (rand 0))]
               [:stroke-weight 2]]
    (q/ellipse (-> ranged-frame
                   (* 41)
                   q/cos
                   (* 60)
                   (+ 70))
               (-> ranged-frame
                   (* 10)
                   q/sin
                   (* 250)
                   (+ 300))
               (-> ranged-frame
                   (* 20)
                   q/cos
                   (* 0)
                   (+ 20)) 20)))


(defn draw-state [state]
  (with-frames (q/frame-count) 1 360 0.35 150 true
    (q/background 255 215 86)
    (q/no-stroke)
    (with-style [[:fill 200 100 50]
                 [:translate 80 300]
                 [:text-size 40]]
      (with-style [[:no-fill]
                   [:stroke-weight 4]
                   [:stroke 200 100 50]]
        (q/rect (- 20) (- 50) 460 110))
      (q/text-font (q/create-font "Helvetica" 50 true))
      (q/text "Seu filho se perdeu" 0 0)
      (q/text "no verão de Recife" 0 50))
    (with-style [[:fill 108 204 184]
                 [:translate (/ (q/width) 1.4) 0]]
      #_(q/rect 0
              0
              (/ (q/width) 2)
              (/ (q/height) 1))
      (doseq [i (range N)]
        (with-style [[:translate 35 (* 0.01 (q/sin (* i q/TWO-PI)))]]
          (if (= i 400)
            (q/fill 100 20 10))
          (burn (+ 0
                   (-> ranged-frame
                       (* q/HALF-PI)
                       q/cos
                       (* 0.005)
                       (+ 0.03)
                       (* (q/map-range i 0 N 300 400))
                       (+ 200)))))))))


(q/defsketch Verão-1
  :title "Paulo Feodrippe - Verão 1"
  :size [720 720]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
