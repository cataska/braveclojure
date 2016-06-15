(ns braveclojure.excercises.ch3)

;; 1. Use the str, vector, list, hash-map, and hash-set functions.
(str "Hello " "world")
(vector 1 2 3)
(list 1 2 3)
(hash-map :a 1 :b 2)
(hash-set 1 1 2 2 3 3 4 4 5 5)

;; 2. Write a function that takes a number and adds 100 to it.
(defn add-100 [x]
  (+ x 100))

;; 3. Write a function, dec-maker, that works exactly like the function inc-maker except with subtraction.
(defn dec-maker
  "Create a custom decrementor"
  [dec-by]
  #(- % dec-by))

;; 4. Write a function, mapset, that works like map except the return value is a set.
(defn mapset
  [f coll]
  (set (map f coll)))

;; 5. Create a function that’s similar to symmetrize-body-parts except that it has to work with weird space aliens with radial symmetry. Instead of two eyes, arms, legs, and so on, they have five.
(def asym-alien-parts
  [{:name "head" :size 3}
   {:name "1-eye" :size 1}
   {:name "1-ear" :size 1}
   {:name "mouth" :size 1}
   {:name "1-hand" :size 1}])

(defn matching-alien-part
  [part index]
  {:name (clojure.string/replace (:name part) #"^1-" (str index "-"))
   :size (:size part)})

(defn make-alien-parts
  [part]
  (let [count 5]
    (loop [i 1
           results []]
      (if (> i count)
        results
        (recur (inc i) (conj results (matching-alien-part part i)))))))

(defn symmetrize-alien-parts
  [asym-alien-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (make-alien-parts part))))
    []
    asym-alien-parts))

;; 6. Create a function that generalizes symmetrize-body-parts and the function you created in Exercise 5. The new function should take a collection of body parts and the number of matching body parts to add. If you’re completely new to Lisp languages and functional programming, it probably won’t be obvious how to do this. If you get stuck, just move on to the next chapter and revisit the problem later.
(defn matching-part
  [part index]
  {:name (clojure.string/replace (:name part) #"^1-" (str index "-"))
   :size (:size part)})

(defn make-body-parts
  [part count]
  (loop [i 1
         results []]
    (if (> i count)
      results
      (recur (inc i) (conj results (matching-part part i))))))

(defn symmetrize-body-parts
  [asym-body-parts number]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (make-body-parts part number))))
    []
    asym-body-parts))
