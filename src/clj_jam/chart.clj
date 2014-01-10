(ns clj-jam.chart
  "Chart the package's download rate from the marmalade api result."
  (:use [incanter core stats charts])
  (:require [clj-jam.api :as a]
            [clojure.string :as s]
            []))

(def versions ^{:doc "List of current org-trello versions. FIXME: Need to fetch this from marmalade."}
  (for [medium (range 0 3)
        minor (range 0 10)
        :let [version (format "0.%s.%s" medium minor)]
        :when (not (#{"0.0.0" "0.0.1" "0.0.2" "0.2.9"} version))]
    version))

;; simulate result from the api namespace
(def actual-result (into (sorted-map) {"0.0.3" 2
                                       "0.0.4" 1
                                       "0.0.5" 10
                                       "0.0.6" 1
                                       "0.0.7" 13
                                       "0.0.8" 20
                                       "0.0.9" 5
                                       "0.1.0" 16
                                       "0.1.1" 8
                                       "0.1.2" 8
                                       "0.1.3" 10
                                       "0.1.4" 2
                                       "0.1.5" 5
                                       "0.1.6" 10
                                       "0.1.7" 2
                                       "0.1.8" 25
                                       "0.1.9" 13
                                       "0.2.0" 20
                                       "0.2.1" 0
                                       "0.2.2" 57
                                       "0.2.3" 14
                                       "0.2.4" 1
                                       "0.2.5" 3
                                       "0.2.6" 46
                                       "0.2.7" 2
                                       "0.2.8" 16}))

(defn barchart-by-versions [versions-values-map] "Given a map of versions, compute its equivalent barchart."
  (let [prepare-version #(clojure.string/replace % "." "")
        versions-values (reduce (fn [[vss vls] [vs vl]]
                                  [(conj vss (prepare-version vs)) (conj vls vl)])
                                [[] []]
                                versions-values-map)]
    (view (bar-chart (first versions-values) (second versions-values)))))

(barchart-by-versions2 actual-result)
