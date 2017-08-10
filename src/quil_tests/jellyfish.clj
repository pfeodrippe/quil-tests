(ns quil-tests.jellyfish
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-tests.helpers :refer :all :as qh]))

;; example from https://twitter.com/beesandbombs
;; https://gist.github.com/beesandbombs/253a8c61a207c44ee232a76c4a8b79da

(def N 17)
(def r 250)


(defn real-op [a b op th]
  (op (* (q/cos th) a)
      (* (q/sin th) b)))


(defn waving-vertex [x y ranged-frame]
  (let [dd (-> (q/dist x y 0 0)
               (/ r))
        th (-> ranged-frame
               (* q/TWO-PI)
               (- (* q/HALF-PI (q/sq dd)))
               q/sin
               (* q/PI)
               (/ 6))]
    (q/vertex (real-op x y + th)
              (real-op y x - th)
              (-> ranged-frame
                  (* q/TWO-PI)
                  (- (* 3 (q/sq dd)))
                  q/cos
                  (* 90)))))


(defn lines [ranged-frame]
  (doseq [i (range N)]
    (let [qq (/ i (float (dec N)))]
      (q/stroke (-> (q/sq qq)
                    (* 4 (- 1 qq))
                    (+ 0.25 ranged-frame)
                    (mod 1))
                1
                1))
    (let [x  (q/map-range i -0.5 (- N 0.5) (- r) r)
          y' (q/sqrt (- (* r r) (* x x)))
          m  (int (+ y' 10))]
      (with-shape
        (doseq [j (range m)]
          (waving-vertex x
                         (q/map-range j 0 (dec m) (- y') y')
                         ranged-frame))))))


(defn setup []
  (q/frame-rate 30)
  (q/smooth 8)
  (q/no-fill)
  (q/stroke 32)
  (q/stroke-weight 3)
  (q/color-mode :hsb 1)
  {})


(def styles
  {:main []})


(defn update-state [state]
  {})


(defn draw-state [state]
  (with-frames (q/frame-count) 1 100 0.6 true
    (q/background 0)
    (with-matrix
      (q/translate (/ (q/width) 2)
                   (- (/ (q/height) 2) 50))
      (q/rotate-x 0.67)
      (lines ranged-frame)
      (with-matrix
        (q/rotate (/ q/TWO-PI 3))
        (lines ranged-frame))
      (with-matrix
        (q/rotate (- (/ q/TWO-PI 3)))
        (lines ranged-frame))
      (with-matrix
        (q/stroke ranged-frame 1 1)
        (q/translate 0 0 (-> ranged-frame
                             (* q/TWO-PI)
                             (+ (* q/TWO-PI 0.525))
                             q/cos
                             (* 90)))
        (q/ellipse 0 0 (* 2 r) (* 2 r))))))


(q/defsketch jellyfish
  :title "Bees and Bombs - Jellyfish"
  :size [800 600]
  :setup setup
  :update update-state
  :draw draw-state
  :renderer :p3d
  :features [:keep-on-top]
  :middleware [m/fun-mode])
