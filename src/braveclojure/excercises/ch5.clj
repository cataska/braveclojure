(ns braveclojure.excercises.ch5)

;; 1. You used (comp :intelligence :attributes) to create a function that returns a character’s intelligence. Create a new function, attr, that you can call like (attr :intelligence) and that does the same thing.
(defn attr
  [k]
  (comp k :attribute))

;; 2. Implement the comp function.
(defn my-comp
  [& fs]
  (fn [& args]
    (let [fns (reverse fs)
          result (apply (first fns) args)]
      (if (empty? fns)
        result
        (reduce
          (fn [r f] (f r))
          result
          (rest fns))))))

;; 3. Implement the assoc-in function.
