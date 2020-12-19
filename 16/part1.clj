(require '[clojure.string :refer [split, split-lines]])

(defn get-range [text]
  (->> text
       (re-matches #"(\d+)-(\d+)")
       next
       (map read-string)
       (interleave [:start :stop])
       (apply hash-map)))

(defn parse-rule [line]
  (-> line
      (split #": ")
      second
      (split #" or ")
      (#(map get-range %))))

(defn parse-rules [rules]
  (->> rules
       split-lines
       (map parse-rule)
       flatten))

(defn parse-tickets [tickets]
  (->> tickets
       split-lines
       next
       (map #(split % #","))
       flatten
       (map read-string)))

(defn parse-blocks [blocks]
  {:rules (parse-rules (first blocks))
   :numbers (parse-tickets (nth blocks 2))})

(defn read-data [f]
  (-> f
      slurp
      (split #"\n\n")
      parse-blocks))

(defn valid? [number rules]
  (some #(and
           (> number (:start %))
           (< number (:stop %)))
        rules))

(defn invalid-numbers [{:keys [rules numbers]}]
  (remove #(valid? % rules) numbers))

(->> "input"
     read-data
     invalid-numbers
     (reduce +)
     println)
