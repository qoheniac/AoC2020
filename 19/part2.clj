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

(defn compile-rules [rulesmap numstr]
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
                 (next todo)))))))

(defn contained? [part valids]
  (some #(= part %) valids))

; effective rule 0: 42 42 … 31 …
; with at least one more 42 than 31s
(defn valid? [message valids42 valids31 length]
  (loop [valids valids42
         count42 0
         count31 0
         todo message]
    (if (empty? todo)
      (and (> count31 0) (> count42 1) (> (- count42 count31) 0))
      (if (contained? (take length todo) valids)
        (recur valids
               (if (= valids valids42) (inc count42) count42)
               (if (= valids valids31) (inc count31) count31)
               (drop length todo))
        (if (= valids valids42)
          (recur valids31 count42 count31 todo)
          false)))))

(defn count-valids [messages valids42 valids31 length]
  (loop [valids-counter 0
         todo messages]
    (if (nil? todo)
      valids-counter
      (recur (if (valid? (first todo) valids42 valids31 length)
               (inc valids-counter)
               valids-counter)
             (next todo)))))

(let [[rules messages] (read-input "input")
      rulesmap (parse-rules rules)
      valids42 (compile-rules rulesmap "42")
      valids31 (compile-rules rulesmap "31")
      length (count (first valids42))]
  (-> messages
      split-lines
      (count-valids valids42 valids31 length)
      println))
