(require '[clojure.string :as string :refer [split split-lines]])

(let [data (split-lines (slurp "input"))
      ets (Integer. (first data))
      ids (map #(Integer/parseInt %)
               (remove #(= "x" %) (split (second data) #",")))
      waits (map #(- % (mod ets %)) ids)]
  (println (apply * (first (sort-by second (map list ids waits))))))
