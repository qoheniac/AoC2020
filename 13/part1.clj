(require '[clojure.string :as string :refer [split split-lines]])

(defn get-data [f]
  (let [line (split-lines (slurp f))]
    {:ets (read-string (first line))
     :ids (map read-string
               (remove #(= "x" %) (split (second line) #",")))}))

(defn waits [data]
  (map #(- % (mod (:ets data) %)) (:ids data)))

(defn sort-by-first [x y]
  (sort-by first (map list x y)))

(let [data (get-data "input")]
  (println (apply * (first (sort-by-first (waits data) (:ids data))))))
