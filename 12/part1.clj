(require '[clojure.string :as string :refer [split-lines]])

(defn abs [x]
  (if (> x 0) x (- x)))

(defn read-data [f]
  (map #(let [match (next (re-matches #"([A-Z])(\d+)" %))]
          (list (first match) (Integer. (second match))))
       (split-lines (slurp f))))

(defn move [loc dir vel]
  (case dir
    "N" (list (first loc) (+ (second loc) vel))
    "E" (list (+ (first loc) vel) (second loc))
    "S" (list (first loc) (- (second loc) vel))
    "W" (list (- (first loc) vel) (second loc))))

(defn rotate [dir angle]
  (case angle
    90 (case dir "N" "E" "E" "S" "S" "W" "W" "N")
    180 (case dir "N" "S" "E" "W" "S" "N" "W" "E")
    270 (case dir "N" "W" "E" "N" "S" "E" "W" "S")))

(defn exec [loc dir cmd]
  (let [action (first cmd) number (second cmd)]
    (if (= action "F")
      (list (move loc dir number) dir)
      (if (= action "R")
        (list loc (rotate dir number))
        (if (= action "L")
          (list loc (rotate dir (- 360 number)))
          (list (move loc action number) dir))))))

(loop [loc (list 0 0) dir "E" cmds (read-data "input")]
  (if (nil? cmds)
    (println (apply + (map abs loc)))
    (let [locdir (exec loc dir (first cmds))]
      (recur (first locdir) (second locdir) (next cmds)))))
