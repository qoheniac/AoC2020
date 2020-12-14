; t == -IDXi + IDi * Ni, d. h. (t + IDXi) % IDi == 0 (IDX: Index == Zeitversatz)
; 1. Sortiere Busse absteigend nach ID (Beginne mit Bus mit maximaler Schrittweite)
; 2. Gehe N0 durch bis t Modulo auch für ID1 erfüllt, dann gilt
;    t == -IDX0 + ID0 * N0 == -IDX1 + ID1 * N1
;    und der kleinste Schritt, der die Form der Gleichungen erhält ist ID0*ID1
;    t + IDX0*IDX1 == -IDX0 + ID0 * (N0+IDX1) == -IDX1 + ID1 * (N1+IDX0)
; 3. Fahre so fort bis Modulo-Gleichung für alle Busse erfüllt.
(require '[clojure.string :as string :refer [split split-lines]])
(let [fulldata (sort-by second > (remove nil? (map-indexed #(if (= "x" %2) nil (list %1 (Integer. %2))) (split (second (split-lines (slurp "input"))) #","))))
      maxts (first (first fulldata))
      maxid (second (first fulldata))]
  (loop [data fulldata modtimestemp (- maxid maxts) timestep maxid]
    (if (nil? (next data))
      (println modtimestemp)
      (recur
        (next data)
        (loop [timestemp modtimestemp]
          (if (== 0 (mod (+ timestemp (first (second data))) (second (second data))))
            timestemp
            (recur (+ timestemp timestep))))
        (* timestep (second (second data)))))))
