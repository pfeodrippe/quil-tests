(ns quil-tests.test3
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-tests.helpers :refer :all :as qh]))

;; example from https://twitter.com/beesandbombs
;; https://gist.github.com/beesandbombs/e19e12501eec56e495f94f46942ab965

(def N 12000)
(def R 270)
(def ss 4)
(def h 1E-5)


(defn x [v]
  (let [th (-> v q/sqrt (* 14 q/TWO-PI))
        r  (-> v q/sqrt (* R))]
    (-> th q/cos (* r))))


(defn y [v]
  (let [th (-> v q/sqrt (* 14 q/TWO-PI))
        r  (-> v q/sqrt (* R))]
    (-> th q/sin (* r))))


(defn deriv [f v h]
  (-> v
      (+ h)
      f
      (- (f v))
      (/ h)))


(defn xp [v]
  (deriv x v h))


(defn yp [v]
  (deriv y v h))


(defn xpn [v]
  (-> v
      xp
      (/ (q/dist (xp v)
                 (yp v)
                 0.0
                 0.0))))


(defn ypn [v]
  (-> v
      yp
      (/ (q/dist (xp v)
                 (yp v)
                 0.0
                 0.0))))


(defn setup []
  (q/frame-rate 30)
  (q/smooth 8)
  (q/no-fill)
  (q/stroke 32)
  (q/stroke-weight 1.2)
  (q/rect-mode :center)
  {})


(def styles
  {:main []})


(defn update-state [state]
  {})


(defn draw-state [state]
  (with-frames (q/frame-count) 4 320 60
    (q/background 250)
    (with-matrix
      (q/translate (/ (q/width) 2)
                   (- (/ (q/height) 2) 50))
      (q/rotate-x 0.7)
      (with-shape
        (doseq [i (range 0 N)]
          (let [qq (/ i (float (- N 1)))
                ss (-> (q/map-range (-> ranged-frame
                                        (* q/TWO-PI)
                                        (- (* qq q/TWO-PI)))
                                    1
                                    -1
                                    0
                                    1)
                       (* 2.5)
                       (- 1.5)
                       (q/constrain 0 1)
                       (ease 4)
                       (* 6))
                tw (-> ranged-frame
                       (* 3 q/TWO-PI)
                       (+ (* qq 3200)))
                sw (let [temp-sw (q/map-range ss 0 6 1.5 2.5)]
                     (if (>= qq 0.95)
                       (-> qq (q/map-range 0.95 1 1 0) ease (* temp-sw))
                       temp-sw))]
            (q/stroke-weight sw)
            (q/vertex (-> qq x (+ (* ss (q/cos tw) (ypn qq))))
                      (-> qq y (- (* ss (q/cos tw) (xpn qq))))
                      (-> tw q/sin (* ss)))))))))


(q/defsketch spiral-spread
  :title "Bees and Bombs - Spiral Spread"
  :size [720 720]
  :setup setup
  :update update-state
  :draw draw-state
  :renderer :p3d
  :features [:keep-on-top]
  :middleware [m/fun-mode])
