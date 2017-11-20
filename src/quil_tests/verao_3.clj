(ns quil-tests.verao-3
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-tests.helpers :refer :all]))

;; Author: Paulo Feodrippe

(def N 50)


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


(defn draw-state [state]
  (with-frames (q/frame-count) 1 150 0.35 150 false
    (q/background 255 223 219)
    (q/no-stroke)
    (with-style [[:fill 200 202 204]
                 [:translate 80 300]]
      (q/text-font (q/create-font "Helvetica" 43 true))
      (with-style [[:fill 200 202 204]]
        (q/translate (-> ranged-frame
                         (* 16)
                         q/cos
                         (* 100)
                         (+ 100))
                     0)
        (q/text "pai" 306 50))
      (with-style [[:fill 255 223 219]]
        (q/rect 442 10 150 50))
      (with-style [[:no-fill]
                   [:stroke-weight 4]
                   [:stroke 200 202 204]]
        (q/rect (- 20) (- 50) 460 110))
      (q/text "Mas ouve-se a notícia" 0 0)
      (q/text "de seu ausente" 0 50))
    (with-style [[:translate 532 330]
                 [:fill 200 202 204]]
      (with-style [[:stroke 250 202 204]
                   [:translate 0 (- 110)]]
        (q/stroke-weight 2)
        (doseq [i (range 10)]
          (q/line (* i 6) 0 (* i 6) 320)))
      (let [v (-> ranged-frame
                  (* 16)
                  q/cos
                  (* 100))]
        (doseq [i (range 10)
                j (range N)]
          (q/ellipse (* i 6)
                     (+ v (* j 2))
                     5
                     5))))))


(q/defsketch Verão-3
  :title "Paulo Feodrippe - Verão 3"
  :size [720 720]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
