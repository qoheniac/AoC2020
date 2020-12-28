(require '[clojure.string :refer [split split-lines]]
         '[clojure.math.combinatorics :refer [cartesian-product]])

(defn read-input [file]
  (split (slurp file) #"\n\n"))

(defn parse-block [block]
  (let [lines (split-lines block)
        tile (rest lines)]
    {(read-string (re-find #"\d+" (first lines)))
     {:top (vec (first tile)),
      :bottom (vec (last tile)),
      :left (mapv first tile),
      :right (mapv last tile)}}))

(defn sides [tile]
  (vals (val tile)))

(defn neighbors? [tile1 tile2]
  (some #(apply = %)
        (cartesian-product
          (sides tile1)
          (let [sides2 (sides tile2)]
            (concat sides2
                    (map reverse sides2))))))

(defn corner? [tile tiles]
  (== 2
      (loop [neighbors 0
             todo (dissoc tiles (key tile))]
        (if (empty? todo)
          neighbors
          (recur
            (if (neighbors? tile (first todo))
              (inc neighbors)
              neighbors)
            (rest todo))))))

(defn find-corners [tiles]
  (loop [corners []
         todo tiles]
    (if (empty? todo)
      corners
      (let [tile (first todo)]
        (recur
          (if (corner? tile tiles)
            (conj corners (key tile))
            corners)
          (rest todo))))))

(->> "input"
     read-input
     (map parse-block)
     (apply merge)
     find-corners
     (reduce *)
     println)
