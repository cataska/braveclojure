build-lists: true

# Organization Your Project
## A Librarian’s Tale

---

![fit](Melvil_Dewey.jpg)

---

## Metaphor

- Library -> Namespace
- Book name -> Symbol
- Card caralog -> Var
- Shelf address  -> Memory space

---

# Symbol

```clojure
inc
; => #object[clojure.core$inc 0x4aa0a65c "clojure.core$inc@4aa0a65c"]
'inc
; => inc
```

---

# Var

---

# Storing Objects with def

```clojure
(def great-books ["East of Eden" "The Glass Bead Game"]) ; => #'user/great-books
```

---

# Reader form of a var

```clojure
#'user/great-books
```

---

# Interning a var

1. Update the current namespace’s map with the association between great-books and the var.
1. Find a free storage shelf.
1. Store ["East of Eden" "The Glass Bead Game"] on the shelf.
1. Write the address of the shelf on the var.
1. Return the var (in this case, #'user/great-books).

---

# Map of symbols-to-interned-vars

```clojure
(ns-interns *ns*)
```

---

# Deref the var

```clojure
(deref #'user/great-books)
; => ["East of Eden" "The Glass Bead Game"]
```

---

# Namespace

---

# Current namespace

```clojure
(ns-name *ns*) ; => user
```

---

# Creating and switching namespace

```clojure
(create-ns 'cheese.taxonomy) ; => #object[clojure.lang.Namespace 0x221c0ed3 "cheese.taxonomy"]
(in-ns 'cheese.taxonomy)
```

---

# Use functions and data from other namespaces

---

# Fully qualified symbol

```clojure
(in-ns 'cheese.taxonomy)
(def cheddars ["mild" "medium" "strong" "sharp" "extra sharp"])
(in-ns 'cheese.analysis)

; cheddars => java.lang.RuntimeException: Unable to resolve symbol
; cheese.taxonomy/cheddars => ["mild" "medium" "strong" "sharp" "extra sharp"]
```

---

# refer

```clojure
(in-ns 'cheese.taxonomy)
(def cheddars ["mild" "medium" "strong" "sharp" "extra sharp"])
(def bries ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"])
(in-ns 'cheese.analysis)
(clojure.core/refer 'cheese.taxonomy)

; bries => ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]
; cheddars => ["mild" "medium" "strong" "sharp" "extra sharp"]
```

---

# refer

:only

```clojure
(clojure.core/refer 'cheese.taxonomy :only ['bries])
; bries => ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]
; cheddars => java.lang.RuntimeException: Unable to resolve symbol: cheddars in this context
```

---

# refer

:exclude

```clojure
(clojure.core/refer 'cheese.taxonomy :exclude ['bries])
; bries => java.lang.RuntimeException: Unable to resolve symbol: bries in this context
; cheddars => ["mild" "medium" "strong" "sharp" "extra sharp"]
```

---

# refer

:rename

```clojure
(clojure.core/refer 'cheese.taxonomy :rename {'bries 'yummy-bries})
; bries => java.lang.RuntimeException: Unable to resolve symbol: bries in this context
; yummy-bries => ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]
```

---

# alias

```clojure
(clojure.core/alias 'taxonomy 'cheese.taxonomy)
; taxonomy/bries => ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]
```

---

# Loading namespace in your project

Clojure doesn’t automatically evaluate it when it runs your project; you have to explicitly tell Clojure that you want to use it.

---

# Loading namespace in your project

```clojure
(require 'the-divine-cheese-code.visualization.svg)
; the_divine_cheese_code/visualization/svg.clj
```

---

# require

:as

```clojure
(require '[the-divine-cheese-code.visualization.svg :as svg])
```

is equivalent to this:

```clojure
(require 'the-divine-cheese-code.visualization.svg)
(alias 'svg 'the-divine-cheese-code.visualization.svg)
```

---

# use

```clojure
(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg)
```

is equivalent to this:

```clojure
(use 'the-divine-cheese-code.visualization.svg)
```

---

# use

:as :only :exclude

```clojure
(use 'the-divine-cheese-code.visualization.svg :as svg :only [points])
```

---

# The ns Macro

- Create new namespace if needed.
- Refer **clojure.core** namespace by default.

---

# Six possible kinds of references within ns

- (:refer-clojure)
- (:require)
- (:use)
- (:import)
- (:load)
- (:gen-class)

---

# The ns macro
## (:require)

```clojure
(ns the-divine-cheese-code.core
  (:require the-divine-cheese-code.visualization.svg))
```

is equivalent to this:

```clojure
(in-ns 'the-divine-cheese-code.core)
(require 'the-divine-cheese-code.visualization.svg)
```

---

# The ns macro
## (:require) alias a library

```clojure
(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :as svg]))
```

is equivalent to this:

```clojure
(in-ns 'the-divine-cheese-code.core)
(require ['the-divine-cheese-code.visualization.svg :as 'svg])
```

---

# One more thing...

---

# Private function

```clojure
(defn- private-function
  "Just an example function that does nothing"
  [])
```

---

# References

- [Ch6 of Clojure for the Brave and True](http://www.braveclojure.com/organization/)
- [Namespaces](http://clojure.org/reference/namespaces)
- [Vars and the Global Environment](http://clojure.org/reference/vars)
- [Intern - Clojure Terminology Guide](http://clojure-doc.org/articles/language/glossary.html#intern)
- [Using Libs](http://clojure.org/reference/libs)

---

# THANK YOU
