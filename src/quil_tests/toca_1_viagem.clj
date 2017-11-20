(ns quil-tests.toca-1-viagem
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-tests.helpers :refer :all]))


;; Author: Paulo Feodrippe

(def N 50)


(defn setup []
  (q/frame-rate 60)
  (q/smooth 8)
  (q/no-stroke)
  (q/stroke 10)
  (q/text-font (q/create-font "Georgia" 40 true))
  {:texts [["nunca  em  um  único  segundo"
            "tantas  pessoas  da  minha  vida"]
           ["viajaram  pra  tão  longe  e  eu  ainda"
            "meio  que  entregue  ao  mundo"]
           ["como  brinco  sem  eles"
            "como  filho  bom  que  sou"]
           ["como  amo  sem  ela"
            "ser  ser  perdido  é  o  que  me  restou"]]
   :radius 200})


(def styles
  {})


(defn update-state [state]
  state)


(defn curved-text [state text rotation ranged-frame]
  (let [a (atom 0)]
    (doseq [i (range (count text))
            :let [c (str (get text i))
                  half-width (/ (q/text-width c) 2.0)
                  gap 25
                  r (:radius state)]]
      (swap! a #(+ half-width %))
      (let [theta (+ q/PI
                     (/ @a r 0.7)
                     rotation
                     (-> ranged-frame
                         (* 3.0)
                         q/cos
                         (* 1.5)))]
        (with-matrix #_[[:translate (+ (- gap half-width) (* i gap)) 0]]
          (q/translate (* r (q/cos theta))
                       (* r (q/sin theta)))
          (q/rotate (+ theta q/HALF-PI))
          (swap! a #(+ half-width %))
          (q/text c 0 0))))))


(defn draw-circle
  [state ranged-frame prop]
  (with-style [[:fill (:r prop) (:g prop) (:b prop)]
               [:translate 360 360]]
    (q/rotate 0.00)
    (q/text-font (q/create-font "Georgia" 24 true))
    (curved-text state (get-in state (concat [:texts] (:t1 prop))) 0 ranged-frame)
    (with-style [[:translate 0 0]]
      (curved-text state (get-in state (concat [:texts] (:t2 prop))) q/PI ranged-frame)))
  (with-style [[:translate 360 360]]
    (q/no-fill)
    (q/stroke (:r prop) (:g prop) (:b prop))
    #_(255 119 111)
    (q/ellipse 0 0 (* (:radius state) 1.95) (* (:radius state) 1.9))))


(defn factor
  [delay]
  (-> delay
      (* 2.0)
      q/sin
      (* 20.0)))


(defn draw-state [state]
  (with-frames (q/frame-count) 1 150 0.35 900 true
    (q/background 244 255 136)
    (q/no-stroke)
    (q/no-fill)
    (with-style [[:scale 1.2]
                 [:translate (factor (- ranged-frame 0.0)) 0]]
      (draw-circle state (- ranged-frame 0.0) {:t1 [0 0]
                                               :t2 [0 1]
                                               :r 255
                                               :g 119
                                               :b 111}))
    (with-style [[:scale 1.0]
                 [:translate (factor (- ranged-frame 0.1)) 0]]
      (draw-circle state (- ranged-frame 0.2) {:t1 [1 0]
                                               :t2 [1 1]
                                               :r 68
                                               :g 137
                                               :b 204}))
    (with-style [[:scale 0.8]
                 [:translate (factor (- ranged-frame 0.24)) 0]]
      (draw-circle state (- ranged-frame 0.4) {:t1 [2 0]
                                               :t2 [2 1]
                                               :r 255
                                               :g 119
                                               :b 111}))
    (with-style [[:scale 0.6]
                 [:translate (factor (- ranged-frame 0.5)) 0]]
      (draw-circle state (- ranged-frame 0.6) {:t1 [3 0]
                                               :t2 [3 1]
                                               :r 68
                                               :g 137
                                               :b 204}))))


(q/defsketch Toca-1-Viagem
  :title "Paulo Feodrippe - Toca Viagem"
  :size [720 720]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
