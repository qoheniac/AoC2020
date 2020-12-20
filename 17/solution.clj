(require '[clojure.string :refer [split-lines]])
(require '[clojure.math.combinatorics :refer [selections]])

(defn read-data [f]
  (map seq (split-lines (slurp f))))

(defn parse-data [data dim]
  (remove nil?
          (for [[i row] (map-indexed list data)
                [j cube] (map-indexed list row)]
            (if (= \# cube)
              (concat [j (- i)]
                      (repeat (- dim 2) 0))
              nil))))

(defn add [u v]
  (map + u v))

(defn get-neighbors [cube dim]
 (map #(add cube %)
      (remove #(= % (apply vector (repeat dim 0)))
              (selections [-1 0 1] dim))))

(defn active? [actives neighbor]
  (some #(= % neighbor) actives))

(defn count-active-neighbors [actives neighbors]
  (count (filter #(active? actives %) neighbors)))

(defn twothree? [n]
  (or (== 2 n) (== 3 n)))

(defn keep-active [actives neighborhoods]
  (let [state (map list actives neighborhoods)]
    (map first
         (filter #(twothree? (count-active-neighbors actives (second %)))
                 state))))

(defn make-active [actives neighborhoods]
  (->> neighborhoods
       (reduce concat)
       (remove #(active? actives %))
       frequencies
       (filter #(== 3 (val %)))
       (map first)))

(defn boot [actives dim]
  (let [neighborhoods (map #(get-neighbors % dim) actives)]
    (lazy-seq (cons actives
                    (boot (concat
                            (keep-active actives neighborhoods)
                            (make-active actives neighborhoods))
                          dim)))))

(defn exec [dim]
  (-> "input"
      read-data
      (parse-data dim)
      (boot dim)
      (nth 6)
      count))

(printf "Part 1: %d%n" (exec 3))
(printf "Part 2: %d%n" (exec 4))
