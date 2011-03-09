(ns window.core
  (:gen-class)
  (:import (java.awt Canvas Color Frame Graphics Toolkit Window))
  (:import (java.awt.event WindowAdapter)))

(defn main-window
  "properties are map with following keys:
  :size-x
  :size-y
  :title
  :visible"
  [& properties]
  (let [default {::size-x 0 :size-y 0 :title "" :listener nil :visible false :canvas nil}
        {:keys [title size-x size-y visible listener canvas]} (merge default (apply hash-map properties))]
    (doto (Frame.)
      (.setTitle title)
      (.setSize size-x size-y)
      (.addWindowListener listener)
      (.setVisible visible)
      (.add canvas))))

(defmacro window-adapter
  [body]
  `(proxy [WindowAdapter] []
    ~body))

(def listener
  (window-adapter
    (windowClosing
      [event]
      (.dispose (.getWindow event)))))

(def canvas (Canvas.))

(def root-window 
  (main-window
    :size-x 640
    :size-y 480
    :title "stuff"
    :listener listener
    :visible true
    :canvas canvas))

(.createBufferStrategy canvas 2)

(defn draw [^Canvas canvas draw-fn]
  (let [buffer (.getBufferStrategy canvas)
        g      (.getDrawGraphics buffer)]
    (try
      (draw-fn g)
      (finally (.dispose g)))
    (if-not (.contentsLost buffer)
      (.show buffer))
    (.sync (Toolkit/getDefaultToolkit))))

(defn draw-line
  [^Graphics g x1 y1 x2 y2]
  (doto g
    (.setColor Color/black)
    (.drawLine x1 y2 x2 y2)))

(defn -main [])
