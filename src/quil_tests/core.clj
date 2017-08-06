(ns quil-tests.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

;; example from https://twitter.com/beesandbombs
;; https://gist.github.com/anonymous/9f7e57ab4ec7d8073e545e36db1ecf64

(defmacro with-matrix
  [& body]
  `(do
     (q/push-matrix)
     ~@body
     (q/pop-matrix)))


(def samples-per-frame 8)

(def num-frames 360)

(def shutter-angle 0.35)

(def N 12)


(defn setup []
  (q/frame-rate 30)
  (q/smooth 8)
  (q/fill 32)
  (q/no-stroke)
  {})


(defn update-state [state]
  {})


(defn draw-state [state]
  (doseq [sa (range 0 samples-per-frame)]
    (let [t (q/map-range (+ (dec (q/frame-count))
                            (-> sa (* shutter-angle) (/ samples-per-frame)))
                         0
                         num-frames
                         0
                         1)]
      (q/background 250)
      (with-matrix
        (q/translate (/ (q/width) 2) 160)
        (doseq [i (range 0 N)]
          (let [l (q/map-range i -0.75 (dec N) 0 320)]
            (q/stroke 32)
            (q/no-fill)
            (q/arc 0 0 (* 2 l) (* 2 l) 0 q/PI)
            (q/fill 32)
            (q/no-stroke)
            (with-matrix
              (q/rotate (-> t (* (- N i)) (* q/TWO-PI) q/sin (* q/HALF-PI)))
              (q/translate 0 l)
              (q/ellipse 0 0 14 14))))))))


(q/defsketch test1
  :title "Bees and Bombs - Dots"
  :size [700 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
