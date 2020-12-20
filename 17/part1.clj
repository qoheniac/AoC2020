(require '[clojure.string :refer [split-lines]])
(require '[clojure.math.combinatorics :refer [selections]])

(defn read-data [f]
  (map seq (split-lines (slurp f))))

(defn parse-data [data]
  (remove nil?
          (for [[i row] (map-indexed list data)
                [j cube] (map-indexed list row)]
            (if (= \# cube) [j (- i) 0] nil))))

(defn add [u v]
  (map + u v))

(defn get-neighbors [cube]
 (map #(add cube %)
      (remove #(= % [0 0 0])
              (selections [-1 0 1] 3))))

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

(defn boot [actives]
  (let [neighborhoods (map get-neighbors actives)]
    (lazy-seq (cons actives
                    (boot (concat
                            (keep-active actives neighborhoods)
                            (make-active actives neighborhoods)))))))

(-> "input"
    read-data
    parse-data
    boot
    (nth 6)
    count
    println)
