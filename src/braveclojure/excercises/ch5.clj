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
(defn my-assoc-in
  [m [k & ks] v]
  (if ks
    (assoc m k (my-assoc-in (get m k) ks v))
    (assoc m k v)))

;; 4. Look up and use the update-in function.
(def users [{:name "James" :age 26}  {:name "John" :age 43}])
(update-in users [1 :age] inc)

(update-in {:a 3} [:a] / 4 5)

;; 5. Implement update-in.
(defn my-update-in
  [m [k & ks] f & args]
  (if ks
    (assoc m k (my-update-in (get m k) ks f args))
    (assoc m k (apply f (get m k) args))))
