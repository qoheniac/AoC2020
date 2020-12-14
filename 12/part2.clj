(require '[clojure.string :as string :refer [split-lines]])

(defn abs [x]
  (if (> x 0) x (- x)))

(defn read-data [f]
  (map #(let [match (next (re-matches #"([A-Z])(\d+)" %))]
          (list (first match) (Integer. (second match))))
       (split-lines (slurp f))))

(defn move [loc dir vel]
  (list
    (+ (first loc) (* vel (first dir)))
    (+ (second loc) (* vel (second dir)))))

(defn rotate [wploc angle]
  (case angle
    90 (list (second wploc) (- (first wploc)))
    180 (map - wploc)
    270 (list (- (second wploc)) (first wploc))))

(defn exec [loc wploc cmd]
  (let [action (first cmd) number (second cmd)]
    (if (= action "F")
      (list (move loc wploc number) wploc)
      (if (= action "R")
        (list loc (rotate wploc number))
        (if (= action "L")
          (list loc (rotate wploc (- 360 number)))
          (list loc (case action
                      "N" (move wploc (list 0 1) number)
                      "E" (move wploc (list 1 0) number)
                      "S" (move wploc (list 0 -1) number)
                      "W" (move wploc (list -1 0) number))))))))

(loop [loc (list 0 0) wploc (list 10 1) cmds (read-data "input")]
  (if (nil? cmds)
    (println (apply + (map abs loc)))
    (let [locwploc (exec loc wploc (first cmds))]
      (recur (first locwploc) (second locwploc) (next cmds)))))
