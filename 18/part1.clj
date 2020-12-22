(require '[clojure.string :refer [split-lines]])

(defn calc

  ([expression]
   (let [sym (first expression)
         todo (next expression)]
     (if (= sym \))
       (apply calc (calc todo))
       (calc (Character/digit sym 10) todo))))
  
  ([number expression]
   (let [todo (next expression)]
     (case (first expression)
       nil [number nil]
       \( [number todo]
       (let [[number2 todo2] (calc todo)]
         (case (first expression)
           \+ [(+ number number2) todo2]
           \* [(* number number2) todo2]))))))

(defn parse-line [line]
  (->> line
       reverse
       (remove #(= % \ ))
       calc
       first))

(->> "input"
     slurp
     split-lines
     (map parse-line)
     (reduce +)
     println)
