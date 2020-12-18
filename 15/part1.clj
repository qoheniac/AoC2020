(defn init [start]
  (->> start
       reverse
       (map-indexed #(hash-map %2 %1))
       (apply merge)))

(defn raise [age]
  (apply hash-map
    (interleave
      (keys age)
      (map inc (vals age)))))

(defn game
  ([start] (game (last start) (init start)))
  ([spoken age]
   (lazy-seq
     (let [speak
           (#(if (nil? %) 0 %) (get age spoken))]
       (cons speak
             (game speak
                   (assoc (raise age) spoken 1)))))))

(defn number [times start]
  (nth (game start) (dec (- times (count start)))))

(println (number 2020 [2 1 10 11 0 6]))
