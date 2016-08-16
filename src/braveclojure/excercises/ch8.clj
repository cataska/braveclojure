(ns braveclojure.excercises.ch8)

(def order-details
  {:name "Mitchard Blimmons"
   :email "mitchard.blimmonsgmail.com"})

(def order-details-validations
  {:name
   ["Please enter a name" not-empty]

   :email
   ["Please enter an email address" not-empty

    "Your email address doesn't look like an email address"
    #(or (empty? %) (re-seq #"@" %))]})

(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))

(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
    {}
    validations))

;; 1. Write the macro when-valid so that it behaves similarly to when.
(defmacro when-valid
  [to-validate validations & when-clause]
  `(when (validate ~to-validate ~validations)
    ~@when-clause))

;; 2. Implement or as a macro.
(defmacro my-or
  ([] nil)
  ([x] x)
  ([x & next]
   `(let [or# ~x]
      (if or# or# (my-or ~@next)))))

;; 3. Write a macro that defines an arbitrary number of attribute-retrieving functions using one macro call.
(defmacro defattrs
  ([name value]
   `(def ~name (comp ~value :attributes)))
  ([name value & rest]
   `(do
      (defattrs ~name ~value)
      (defattrs ~@rest))))
