(ns quil-test-01.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))


(def samples-per-frame 8)

(def num-frames 360)

(def shutter-angle 0.35)

(def N 12)


(defn setup []
  (q/frame-rate 30)
  (q/smooth 8)
  (q/fill 32)
  (q/no-stroke)
  {:color 0
   :angle 0
   :radius 0})


(defn update-state [state]
  {:color (mod (+ (:color state) 0.7) 255)
   :angle (+ (:angle state) 0.1)
   :radius (mod (+ (:radius state) 1) 100)})


(defn draw-state [state]
  (q/background 250)
  ;(q/fill (:color state) 255 255)

  (doseq [sa (range 0 samples-per-frame)]
    (let [t (q/map-range (+ (dec (q/frame-count))
                            (-> sa (* shutter-angle) (/ samples-per-frame)))
                         0
                         num-frames
                         0
                         1)]
      (do
        (q/background 250)
        (q/push-matrix)
        (q/translate (/ (q/width) 2) 160)
        (doseq [i (range 0 N)]
          (let [l (q/map-range i -0.75 (dec N) 0 320)]
            (do
              (q/stroke 32)
              (q/no-fill)
              (q/arc 0 0 (* 2 l) (* 2 l) 0 Math/PI)
              (q/fill 32)
              (q/no-stroke)
              (q/push-matrix)
              (q/rotate (-> t (* (- N i)) (* 0.5 Math/PI) Math/sin (* (/ Math/PI 2))))
              (q/translate 0 l)
              (q/ellipse 0 0 14 14)
              (q/pop-matrix))))
        (q/pop-matrix)))))


(q/defsketch quil-test-01
  :title "Bee and Bombs - Dots"
  :size [700 500]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
