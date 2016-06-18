(ns braveclojure.excercises.ch4)

(def filename "suspects.csv")
(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

;; 1. Turn the result of your glitter filter into a list of names.
(defn glitter-filter
  [minimum-glitter records]
  (map :name (filter #(>= (:glitter-index %) minimum-glitter) records)))

;; 2. Write a function, append, which will append a new suspect to your list of suspects.
(defn append
  [suspect records]
  (conj records suspect))

;; 3. Write a function, validate, which will check that :name and :glitter-index are present when you append. The validate function should accept two arguments: a map of keywords to validating functions, similar to conversions, and the record to be validated.
(defn has-name
  [m]
  (contains? m :name))

(defn has-glitter-index
  [m]
  (contains? m :glitter-index))

(def validations {:name has-name :glitter-index has-glitter-index})

(defn validate
  [validations record]
  (every? true? (map #((get validations %) record) vamp-keys)))

;; 4. Write a function that will take your list of maps and convert it back to a CSV string. You’ll need to use the clojure.string/join function.
(defn to-csv
  [records]
  (clojure.string/join "\n"
    (map #(clojure.string/join "," %)
     (map #(vector (:name %) (:glitter-index %)) records))))
