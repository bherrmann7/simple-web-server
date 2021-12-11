
;; A junk drawer

(ns utils)

(defn die [& args]
  (.println System/err (str "ERROR: " (apply str args)))
  (System/exit 3))

