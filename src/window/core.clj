(ns window.core
  (:gen-class)
  (:import (java.awt Frame Window))
  (:import (java.awt.event WindowAdapter)))

(defn main-window
  "properties are map with following keys:
  :size-x
  :size-y
  :title
  :visible"
  [& properties]
  (let [default {:size-x 0 :size-y 0 :title "" :listener nil :visible false}
        properties (merge default (apply hash-map properties))]
    (doto (Frame.)
      (.setTitle (:title properties))
      (.setSize (:size-x properties)
                (:size-y properties))
      (.addWindowListener (:listener properties))
      (.setVisible (:visible properties)))))

(defmacro window-adapter
  [body]
  `(proxy [WindowAdapter] []
    ~body))

(defn -main []
  (let [listener (window-adapter
                  (windowClosing
                   [event]
                   (.dispose (.getWindow event)))) ])
  (main-window
   :size-x 640
   :size-y 480
   :title "stuff"
   :listener listener
   :visible true))
