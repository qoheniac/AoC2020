(defn init [start]
  (->> start
       butlast
       (map-indexed #(hash-map %2 (inc %1)))
       (apply merge)))

(defn game
  ([start] (game (last start)
                 (count start)
                 (init start)))
  ([spoken now history]
   (lazy-seq
     (let [speak
           (#(if (nil? %) 0 (- now %)) (get history spoken))]
       (cons speak
             (game speak
                   (inc now)
                   (assoc history spoken now)))))))

(defn number [times start]
  (nth (game start) (dec (- times (count start)))))

(print "  Test: ") (println (take 7 (game [0 3 6])))
(let [input [2 1 10 11 0 6]]
  (printf "Part 1: %d%n" (number 2020 input))
  (printf "Part 2: %d%n" (number 3E7 input)))
