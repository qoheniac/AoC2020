(require '[clojure.string :as string :refer [split split-lines]])

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

(defn apply-element [element number]
  (let [pos (first element)]
    (case (second element)
      \X number
      \0 (bit-clear number pos)
      \1 (bit-set number pos))))

(defn apply-element-recursive [number allelements]
  (loop [result number
        elements allelements]
    (if (nil? elements)
      result
      (recur
        (apply-element (first elements) result)
        (next elements)))))

(defn apply-mask [mask numstr]
  (->> mask
       reverse
       (map-indexed vector)
       (apply-element-recursive (read-string numstr))))

(defn get-addr [cmd]
  (-> "\\d+"
      re-pattern
      (re-find cmd)))

(defn update-mem [mem mask memline]
  (assoc mem
         (get-addr (:cmd memline))
         (apply-mask mask (:val memline))))

(let [fullcode (read-data "input")]
  (loop [code (next fullcode)
         mask (:val (first fullcode))
         mem nil]
    (if (nil? code)
      (println (reduce + (vals mem)))
      (let [line (first code)]
        (if (= (:cmd line) "mask")
          (recur (next code)
                 (:val line)
                 mem)
          (recur (next code)
                 mask
                 (update-mem mem mask line)))))))
