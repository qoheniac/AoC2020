; t == -IDXi + IDi * Ni, d. h. (t + IDXi) % IDi == 0 (IDX: Index == Zeitversatz)
; 1. Sortiere Busse absteigend nach ID (Beginne mit Bus mit maximaler Schrittweite)
; 2. Gehe N0 durch bis t Modulo auch für ID1 erfüllt, dann gilt
;    t == -IDX0 + ID0 * N0 == -IDX1 + ID1 * N1
;    und der kleinste Schritt, der die Form der Gleichungen erhält ist ID0*ID1
;    t + IDX0*IDX1 == -IDX0 + ID0 * (N0+IDX1) == -IDX1 + ID1 * (N1+IDX0)
; 3. Fahre so fort bis Modulo-Gleichung für alle Busse erfüllt.
(require '[clojure.string :as string :refer [split split-lines]])

(defn read-second-line [f]
  (split (second (split-lines (slurp f))) #","))

(defn read-data [f]
  (->> f
       read-second-line
       (map read-string)
       (map #(hash-map :idx %1 :id %2) (range))
       (remove #(= (symbol 'x) (:id %)))))

(let [allbuses (sort-by :id > (read-data "input"))
      firstbus (first allbuses)]
  (loop [buses (next allbuses)
         modtimestamp (- (:id firstbus) (:idx firstbus))
         timestep (:id firstbus)]
    (if (nil? buses)
      (println modtimestamp)
      (let [bus (first buses)]
        (recur (next buses)
               (loop [timestamp modtimestamp]
                 (if (zero? (mod (+ timestamp (:idx bus)) (:id bus)))
                   timestamp
                   (recur (+ timestamp timestep))))
               (* timestep (:id bus)))))))
