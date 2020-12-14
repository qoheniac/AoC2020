(require '[clojure.string :as string :refer [split-lines]])

(defn read-data [f]
  (map #(let [match (next (re-matches #"([A-Z])(\d+)" %))]
          {:action (first match)
           :number (read-string (second match))})
       (split-lines (slurp f))))

(defn move [loc dir vel]
  (case dir
    "N" [(first loc) (+ (second loc) vel)]
    "E" [(+ (first loc) vel) (second loc)]
    "S" [(first loc) (- (second loc) vel)]
    "W" [(- (first loc) vel) (second loc)]))

(defn rotate [dir angle]
  (case angle
    90 (case dir
         "N" "E"
         "E" "S"
         "S" "W"
         "W" "N")
    180 (case dir
          "N" "S"
          "E" "W"
          "S" "N"
          "W" "E")
    270 (case dir
          "N" "W"
          "E" "N"
          "S" "E"
          "W" "S")))

(defn exec [loc dir cmd]
  (let [action (:action cmd) number (:number cmd)]
    (case action
      "F" {:loc (move loc dir number) :dir dir}
      "R" {:loc loc :dir (rotate dir number)}
      "L" {:loc loc :dir (rotate dir (- 360 number))}
          {:loc (move loc action number) :dir dir})))

(loop [loc [0 0]
       dir "E"
       cmds (read-data "input")]
  (if (nil? cmds)
    (println (apply + (map #(Math/abs %) loc)))
    (let [locdir (exec loc dir (first cmds))]
      (recur (:loc locdir) (:dir locdir) (next cmds)))))
