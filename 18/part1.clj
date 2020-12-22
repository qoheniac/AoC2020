(require '[clojure.string :refer [split-lines]])

(defn calc
  ([expression]
   (let [sym (first expression)
         todo (next expression)]
     (if (= sym \))
       (apply calc (calc todo))
       (calc todo (Character/digit sym 10)))))
  ([expression number]
   (let [todo (next expression)]
     (case (first expression)
       nil [nil number]
       \( [todo number]
       (let [[todo2 number2] (calc todo)]
         (case (first expression)
           \+ [todo2 (+ number number2)]
           \* [todo2 (* number number2)]))))))

(defn parse-line [line]
  (->> line
       reverse
       (remove #(= % \ ))
       calc
       second))

(->> "input"
     slurp
     split-lines
     (map parse-line)
     (reduce +)
     println)
