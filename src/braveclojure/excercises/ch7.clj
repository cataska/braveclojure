(ns braveclojure.excercises.ch7)

;; 1. Use the list function, quoting, and read-string to create a list that, when evaluated, prints your first name and your favorite sci-fi movie.
(eval (list (read-string "println") "cataska, Star Wars"))

;; 2. Create an infix function that takes a list like (1 + 3 * 4 - 5) and transforms it into the lists that Clojure needs in order to correctly evaluate the expression using operator precedence rules.
(defn operator? [op] (or (= op '*) (= op '/) (= op '+) (= op '-)))
(defn operaand? [op] (not (operator? op)))
(def operator-precedence {'* 1 '/ 1 '+ -1 '- -1})

(defn push
  [c v]
  (conj c v))

(defn pop
  [c]
  [(first c) (rest c)])

(defn top [c] (first c))

(defn build-prefix
  [operators operands]
  (loop [operators operators
         operands operands]
    (if (empty? operators)
      (top operands)
      (let [[operator operators] (pop operators)
            [operand-r operands] (pop operands)
            [operand-l operands] (pop operands)]
        (recur operators (push operands (list operator operand-l operand-r)))))))

(defn build-stack
  [operators operands token]
  (loop [operators operators
         operands operands]
    (if (or (empty? operators) (> (operator-precedence token) (operator-precedence (top operators))))
      [operators operands]
      (let [[operator operators] (pop operators)
            [operand-r operands] (pop operands)
            [operand-l operands] (pop operands)]
        (recur operators (push operands (list operator operand-l operand-r)))))))

(defn infix
  [lst]
  (loop [operators '()
         operands '()
         syms lst]
    (if (empty? syms)
      (build-prefix operators operands)
      (let [[token syms] (pop syms)]
        (cond
          (operand? token) (recur operators (push operands token) syms)
          (or (empty? operators) (> (operator-precedence token) (operator-precedence (top operators)))) (recur (push operators token) operands syms)
          (<= (operator-precedence token) (operator-precedence (top operators)))
          (let [[operators operands] (build-stack operators operands token)]
            (recur (push operators token) operands syms)))))))

