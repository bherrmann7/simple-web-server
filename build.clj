(ns build
  (:refer-clojure :exclude [test])
  (:require [org.corfield.build :as bb]))

(defn jar [opts]
  (-> opts
      (assoc :main 'simple-web-server :lib 'net.clojars.top/simple-web-server)
      (bb/clean)
      (bb/uber)))
