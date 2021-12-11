

(ns simple-web-server
  (:gen-class)
  (:require [ring.adapter.jetty]
            [clojure.java.io]
            [clojure.string]))

(defn respond-with [content-type body]
  {:status 200
   :headers {"Content-Type" content-type}
   :body body})

(defn mimetype-for-file [filename]
  (let [last-dot (.lastIndexOf filename ".")
        extension (if (= -1 last-dot) "" (subs filename last-dot))]
    (case extension
      ".css" "text/css"
      ".js" "text/javascript"
      ".yml" "text/x-yaml"
      ".html" "text/html"
      "image/jpeg")))

(defn handler [serve-from-dir request]
  ;; for security it would be good to strip the uri chars to a small white list.
  (let [uri-raw (:uri request)
        uri (if (= uri-raw "/") "/index.html" uri-raw)
        file (clojure.java.io/file (str serve-from-dir "/" uri))]
    (if (clojure.string/includes? uri "..")
      (respond-with "text/html" (str ".. not allowed"))
      (if (.exists file)
        (let [content-type (mimetype-for-file uri)]
          (respond-with content-type file))
        (respond-with "text/html" (str "File not found: " uri))))))

(defn start [serve-from-dir]
  (println "Listenting for web connections at :3000  serving from" serve-from-dir)
  (ring.adapter.jetty/run-jetty #(handler serve-from-dir %1) {:port 3000 :join? true}))

(defn -main [& _]
  (start "."))

;; (-main)

