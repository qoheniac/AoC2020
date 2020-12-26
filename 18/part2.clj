(require '[clojure.string :refer [split-lines]])

(defn calc
  ([expression]
   (let [sym (first expression)
         todo (next expression)]
     (if (= sym \))
       (loop [[todo2 number] (calc todo)]
         (if (= :p (first todo2))
           (calc (next todo2) number)
           (recur (calc todo2 number))))
       (let [[todo2 number] (calc todo (Character/digit sym 10))]
           [todo2 number]))))
  ([expression number]
   (let [sym (first expression)
         todo (next expression)]
     (case sym
       nil [nil number]
       \( [(concat [:p] todo) number]
       \* [(concat [:m] todo) number]
       (let [[todo2 number2] (calc todo)]
         (case sym
           :m [todo2 (* number number2)]
           \+ [todo2 (+ number number2)]))))))

(defn parser [expression]
  (loop [[todo number] (calc expression)]
    (if (nil? todo)
      number
      (recur (calc todo number)))))

(defn parse-line [line]
  (->> line
       reverse
       (remove #(= % \ ))
       parser))

(->> "input"
     slurp
     split-lines
     (map parse-line)
     (reduce +)
     println)
