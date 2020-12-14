(require '[clojure.string :as string :refer [split-lines]])

(defn read-data [f]
  (map #(let [match (next (re-matches #"([A-Z])(\d+)" %))]
          [(first match) (read-string (second match))])
       (split-lines (slurp f))))

(defn move [loc dir vel]
  [(+ (first loc) (* vel (first dir)))
   (+ (second loc) (* vel (second dir)))])

(defn rotate [wploc angle]
  (case angle
    90 [(second wploc) (- (first wploc))]
    180 (map - wploc)
    270 [(- (second wploc)) (first wploc)]))

(defn exec [loc wploc cmd]
  (let [action (first cmd)
        number (second cmd)]
    (case action
      "F" {:loc (move loc wploc number)
           :wploc wploc}
      "R" {:loc loc
           :wploc (rotate wploc number)}
      "L" {:loc loc
           :wploc (rotate wploc (- 360 number))}
          {:loc loc
           :wploc (case action
                 "N" (move wploc [0 1] number)
                 "E" (move wploc [1 0] number)
                 "S" (move wploc [0 -1] number)
                 "W" (move wploc [-1 0] number))})))

(loop [loc [0 0]
       wploc [10 1]
       cmds (read-data "input")]
  (if (nil? cmds)
    (println (apply + (map #(Math/abs %) loc)))
    (let [locwploc (exec loc wploc (first cmds))]
      (recur (:loc locwploc) (:wploc locwploc) (next cmds)))))
