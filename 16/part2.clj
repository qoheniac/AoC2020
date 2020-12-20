(require '[clojure.string :refer [split, split-lines]])

(defn get-range [text]
  (->> text
       (re-matches #"(\d+)-(\d+)")
       next
       (map read-string)
       (interleave [:start :stop])
       (apply hash-map)))

(defn parse-rule [line]
  (let [rule (split line #": ")]
    {(first rule)
     (map get-range
          (split (second rule) #" or "))}))

(defn parse-rules [rules]
  (loop [lines (split-lines rules)
         rulesmap {}]
    (if (nil? lines)
      rulesmap
      (let [line (first lines)
            rule (split line #": ")]
        (recur 
          (next lines)
          (assoc rulesmap
                 (keyword (first rule))
                 (map get-range
                      (split (second rule)
                             #" or "))))))))

(defn parse-ticket [ticket]
  (map read-string (split ticket #",")))

(defn parse-tickets [tickets]
  (map parse-ticket (next (split-lines tickets))))

(defn parse-blocks [blocks]
  {:rules (parse-rules (first blocks))
   :myticket (parse-tickets (second blocks))
   :tickets (parse-tickets (nth blocks 2))})

(defn read-data [f]
  (parse-blocks (split (slurp f) #"\n\n")))

(defn in-range? [number {:keys [start stop]}]
  (and (>= number start)
       (<= number stop)))

(defn number-valid? [number rules]
  (some #(in-range? number %)
        (flatten (vals rules))))

(defn ticket-valid? [ticket rules]
  (every? #(number-valid? % rules) ticket))

(defn valid-tickets [{:keys [rules tickets]}]
  (filter #(ticket-valid? % rules) tickets))

(defn rule-fits-field? [rule field]
  (let [ranges (val rule)]
    (every? #(or (in-range? % (first ranges))
                 (in-range? % (second ranges)))
            field)))

(defn match-rules [rules tickets]
  (let [fields (apply map list tickets)]
    (for [field fields]
      (keys (filter #(rule-fits-field? % field) rules)))))

(defn uniq? [idxmatch]
  (== 1 (count (second idxmatch))))

(defn remove-match [matches rulekw]
  (remove #(= rulekw %) matches))

(defn sort-rule-keys [rules tickets]
  (loop [sortmap {}
         idxmatch (map-indexed vector (match-rules rules tickets))]
    (if (not-any? uniq? idxmatch)
      sortmap
      (let [hit (first (filter uniq? idxmatch))
            idx (first hit)
            rulekw (first (second hit))]
        (recur (assoc sortmap rulekw idx)
               (map #(vector (first %)
                             (remove-match (second %)
                                           rulekw))
                    idxmatch))))))

(let [data (read-data "input")
      myticket (:myticket data)]
  (->> (#(sort-rule-keys (:rules data)
                         (concat (valid-tickets data)
                                 myticket)))
     (remove #(nil? (re-find #"departure" (str (key %)))))
     (map second)
     (map #(nth (first myticket) %))
     (reduce *)
     println))
