(require '[clojure.string :as string :refer [split-lines]])

(defn read-data [f]
  (map #(let [match (next (re-matches #"([A-Z])(\d+)" %))]
          {:action (first match)
           :number (read-string (second match))})
       (split-lines (slurp f))))

(defn move [loc dir vel]
  (case dir
    "N" (list (first loc) (+ (second loc) vel))
    "E" (list (+ (first loc) vel) (second loc))
    "S" (list (first loc) (- (second loc) vel))
    "W" (list (- (first loc) vel) (second loc))))

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
    (if (= action "F")
      {:loc (move loc dir number) :dir dir}
      (if (= action "R")
        {:loc loc :dir (rotate dir number)}
        (if (= action "L")
          {:loc loc :dir (rotate dir (- 360 number))}
          {:loc (move loc action number) :dir dir})))))

(loop [loc (list 0 0)
       dir "E"
       cmds (read-data "input")]
  (if (nil? cmds)
    (println (apply + (map #(Math/abs %) loc)))
    (let [locdir (exec loc dir (first cmds))]
      (recur (:loc locdir) (:dir locdir) (next cmds)))))
