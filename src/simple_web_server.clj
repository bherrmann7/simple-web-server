

(ns simple-web-server
  (:gen-class)
  (:require
   [clojure.java.io]
   [ring.adapter.jetty]
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
      ".json" "application/json"
      ".yml" "text/x-yaml"
      ".html" "text/html"
      ".txt" "text/text"
      ".jpg" "image/jpeg"
      ".png" "image/png"
      ".ico" "image/x-icon"
      "application/octet-stream"
      )))

(defn handler [serve-from-dir request]
  ;; for security it would be good to strip the uri chars to a small white list.
  (let [uri-raw (java.net.URLDecoder/decode (:uri request))
        uri (if (= uri-raw "/") "/index.html" uri-raw)
        file (clojure.java.io/file (str serve-from-dir "/" uri))]
    (if (clojure.string/includes? uri "..")
      (respond-with "text/html" (str ".. not allowed"))
      (if (.exists file)
        (let [content-type (mimetype-for-file uri)]
          (respond-with content-type file))
        (respond-with "text/html" (str "File not found: " uri))))))

(defn start [serve-from-dir keystore-filename keystore-password]
  (let [isSSL (and  keystore-filename keystore-password)
        _ (println "isSSL" isSSL)
        jetty-config (if isSSL
                       {:port 3000 :ssl-port 3443 :keystore keystore-filename :key-password keystore-password}
                       {:port 3000})]
    (println "Listening at http 3000, serving from" serve-from-dir)
    (if isSSL
      (println "  Also listening at https 3443") nil)
    (ring.adapter.jetty/run-jetty #(handler serve-from-dir %1) jetty-config)))

(defn -main [& args]
  (let [keystore-filename (first args)
        keystore-password (second args)]
    (start "." keystore-filename keystore-password)))

;; (-main)

