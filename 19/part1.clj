(require '[clojure.string :refer [split split-lines]]
         '[clojure.math.combinatorics :refer [cartesian-product]])

(defn read-input [f]
  (split (slurp f) #"\n\n"))

(defn parse-subrules [subrules]
  (if (nil? (re-find #"\"[a-z]\"" subrules))
    (map #(split % #" ") (split subrules #" \| "))
    (second (seq subrules))))

(defn add-rule [rule rulesmap]
  (let [[numstr subrules] (split rule #": ")]
    (assoc rulesmap 
           numstr
           (parse-subrules subrules))))

(defn parse-rules [rules]
  (loop [rulesmap {}
         todo (split-lines rules)]
    (if (nil? todo)
      rulesmap
      (recur (add-rule (first todo) rulesmap)
             (next todo)))))

(defn compile-rules
  ([rulesmap]
   (println "Compiling rules …")
   (mapv #(apply str %) (compile-rules rulesmap "0")))
  ([rulesmap numstr]
   (let [rule (get rulesmap numstr)]
     (if (char? rule)
       [rule]
       (loop [return []
              todo rule]
         (if (nil? todo)
           (map flatten return)
           (recur (concat return
                          (apply cartesian-product
                                 (map #(compile-rules rulesmap %)
                                      (first todo))))
                  (next todo))))))))

(defn valid? [message valids]
  (some #(= message %) valids))

(defn count-valids [messages valids]
  (println "Counting valid messages …")
  (loop [valids-counter 0
         counter 1
         todo messages]
    (if (nil? todo)
      valids-counter
      (do (printf "%d/%d" counter (count messages))
          (print "\r")     
          (flush)
          (recur (if (valid? (first todo) valids)
                   (inc valids-counter)
                   valids-counter)
                 (inc counter)
                 (next todo))))))

(let [[rules messages] (read-input "input")]
  (->> rules
       parse-rules
       compile-rules
       time
       (count-valids (split-lines messages))
       time
       println))
