(require '[clojure.string :as string :refer [split split-lines]])
(require '[clojure.math.combinatorics :refer [selections]])

(defn parse-line [line]
  (-> line
      (split #" = ")
      (#(hash-map :cmd (first %)
                  :val (second %)))))

(defn read-data [file]
  (->> file
       slurp
       split-lines
       (map parse-line)))

(defn read-memarg [cmd]
  (-> "\\d+"
      re-pattern
      (re-find cmd)
      read-string))

(defn apply-floats [floatindices baseaddr]
    (for [bitops (selections [bit-set bit-clear] (count floatindices))]
      (loop [combinations (map list bitops floatindices)
             addr baseaddr]
        (if (nil? combinations)
          addr
          (let [combination (first combinations)]
            (recur (next combinations)
                   ((first combination) addr (second combination))))))))

(defn get-addr-list [mask cmd]
  (->> cmd
       read-memarg
       (bit-or (:ormask mask))
       (apply-floats (:floatindices mask))))

(defn update-mem [mem mask memline]
    (apply assoc mem
           (interleave (get-addr-list mask (:cmd memline))
                       (repeat (read-string (:val memline))))))

(defn parse-mask [maskval]
  {:ormask
   (read-string (str "2r" (string/replace maskval #"X" "0")))
   
   :floatindices
   (remove nil? (map-indexed #(if (= \X %2) %1 nil) (reverse maskval)))})

(let [fullcode (read-data "input")]
  (loop [code (next fullcode)
         mask (parse-mask (:val (first fullcode)))
         mem nil]
    (if (nil? code)
      (println (reduce + (vals mem)))
      (let [line (first code)]
        (if (= (:cmd line) "mask")
          (recur (next code)
                 (parse-mask (:val line))
                 mem)
          (recur (next code)
                 mask
                 (update-mem mem mask line)))))))
