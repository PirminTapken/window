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
  [properties]
  (doto (Frame.)
    (.setTitle (if-let [s (:title properties)] s ""))
    (.setSize
      (if-let [x (:size-x properties)] x 0)
      (if-let [y (:size-y properties)] y 0))
    (.addWindowListener (if-let [l (:listener properties)] l nil))
    (.setVisible (if-let [b (:visible properties)] b false))))

(def window-adapter
  (proxy [WindowAdapter] []
      (windowClosing [event] (.dispose (.getWindow event)))))

(defn -main []
  (main-window
    {:size-x 640
     :size-y 480
     :title "stuff"
     :listener window-adapter
     :visible true}))
