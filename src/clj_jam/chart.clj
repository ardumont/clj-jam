(ns clj-jam.chart "Chart the package's download rate from the marmalade api result."
  (:use [incanter core stats charts])
  (:require [clj-jam.api :as a]
            [clojure.string :as s])
  (:import [java.io ByteArrayOutputStream]
           [java.io ByteArrayInputStream]))


(defn barchart-by-versions [versions-values-map] "Given a map of versions, compute its equivalent barchart."
  (bar-chart (map first versions-values-map) (map second versions-values-map)
                   :title "Downloads per Versions"
                   :x-label "Versions"
                   :y-label "Downloads"))

(defn gen-chart-png-outputstream! [chart] "Given a chart, compute the png stream equivalent."
  (let [out-stream (ByteArrayOutputStream.)]
    (save chart out-stream)
    (ByteArrayInputStream. (.toByteArray out-stream))))

(comment ;; usage
  (def versions ^{:doc "List of current org-trello versions."}
    (-> "org-trello"
        a/versions
        a/downloads-per-version))

  ;; simulate result from the api namespace
  (def versions-without-computations (into (sorted-map) {"003" 2
                                                         "0031" 8
                                                         "004" 1
                                                         "0041" 8
                                                         "005" 10
                                                         "006" 1
                                                         "0061" 1
                                                         "007" 13
                                                         "008" 20
                                                         "009" 5
                                                         "010" 16
                                                         "011" 8
                                                         "012" 8
                                                         "013" 10
                                                         "014" 2
                                                         "015" 5
                                                         "016" 10
                                                         "017" 2
                                                         "0171" 4
                                                         "018" 25
                                                         "019" 13
                                                         "020" 20
                                                         "021" 0
                                                         "0211" 1
                                                         "0212" 34
                                                         "022" 57
                                                         "023" 14
                                                         "024" 1
                                                         "025" 3
                                                         "026" 46
                                                         "027" 2
                                                         "028" 19}))

  (def barchart-by-versions-without-computations-val (barchart-by-versions versions-without-computations))
  (view barchart-by-versions-without-computations-val)

  (def barchart-by-versions-val (barchart-by-versions versions))
  (view barchart-by-versions-val)

  (save barchart-by-versions-val "/tmp/barchart-by-version.png"))
