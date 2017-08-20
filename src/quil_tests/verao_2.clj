(ns quil-tests.verao-2
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-tests.helpers :refer :all]))

;; Author: Paulo Feodrippe

(def N 9)


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


(defn siren-1 [ranged-frame]
  (with-style [[:translate 100 390]]
    (with-style [[:fill 178 117 95]]
      (q/rect 0 0 15 15))
    (with-style [[:fill 176 255 125]]
      (q/translate 0 15)
      (q/rotate (-> ranged-frame
                    (* 10)
                    q/cos
                    (q/map-range -1 1 0 0.5)
                    (ease 2.4)
                    (* q/TWO-PI)))
      (q/rect 0 0 15 15))))


(defn siren-2 [ranged-frame]
  (with-style [[:translate 100 390]]
    (with-style [[:fill 178 117 95]]
      (q/rect 0 0 15 15))
    (with-style [[:fill 176 255 125]]
      (q/translate 15 0)
      (q/rotate (-> ranged-frame
                    (* 10)
                    q/cos
                    (q/map-range -1 1 0 0.5)
                    (ease 2.4)
                    (* -1 q/TWO-PI)))
      (q/rect (+ 0) (+ 0) 15 15))))


(defn draw-state [state]
  (with-frames (q/frame-count) 1 150 0.35 150 true
    (q/background 255 186 161)
    (q/no-stroke)
    (with-style [[:fill 108 120 204]
                 [:translate 80 300]
                 [:text-size 40]]
      (with-style [[:no-fill]
                   [:stroke-weight 4]
                   [:stroke 108 120 204]]
        (q/rect (- 20) (- 50) 460 110))
      (q/text-font (q/create-font "Helvetica" 50 true))
      (q/text "Alguns acham que" 0 0)
      (q/text "foi a polícia" 0 50))
    (doseq [i (range N)
            j (range N)]
      (with-style [[:translate (* i 40) (* j 35)]]
        (siren-1 (- ranged-frame
                    (* i j 0.01)))
        (siren-2 (- ranged-frame
                    (* i j 0.01)))))))


(q/defsketch Verão-2
  :title "Paulo Feodrippe - Verão 2"
  :size [720 720]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
