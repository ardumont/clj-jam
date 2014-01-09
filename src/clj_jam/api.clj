(ns clj-jam.api
  "Query the marmalade api (the basic authentication scheme is implemented here)"
  (:require [clj-jam.query  :as q]
            [clojure.string :as s]))

(defn user "Retrieve user"
  [name]
  {:method :get
   :uri    (format "/v1/users/%s" name)})

;; (q/execute (user "ardumont"))
;; {:message "Got ardumont", :user {:name "ardumont", :packages ["org-trello"], :created 1372878705}}

(defn token "Retrieve token"
  [user pass]
  {:method :post
   :uri    (format "/v1/users/login?name=%s&password=%s" user pass)})

;; (-> (token "ardumont" "some-pass")
;;     q/execute)

;; {:message "Logged in as \"ardumont\"", :name "ardumont", :token ""}
;; {:message "Logged in as \"username\"", :name "username", :token "some-token"}

(defn package "Retrieve the information about the package"
  ([pack]
     {:method :get
      :uri (format "/v1/packages/%s" pack)})
  ([pack version]
     (-> (package pack)
         (update-in [:uri] (fn [o n] (format "%s/%s" o n)) version))))

;; (-> (package "dash" "0.2.1")
;;     q/execute)

;; (-> "org-trello"
;;     (package "0.0.3")
;;     q/execute)

;; (def map-temp {:message "Got org-trello, version 0.2.5",
;;                :package {:downloads 365,
;;                          :name "org-trello",
;;                          :owners {:ardumont "eniotna.t@gmail.com"},
;;                          :versions [{:requires [["org" [8 0 7]] ["dash" [1 5 0]] ["request" [0 2 0]] ["cl-lib" [0 3 0]] ["json" [1 2]] ["elnode" [0 9 9 7 6]] ["esxml" [0 3 0]] ["s" [1 7 0]] ["kv" [0 0 17]]],
;;                                      :commentary nil,
;;                                      :name "org-trello",
;;                                      :version [0 2 5],
;;                                      :downloads 3,
;;                                      :created 1385249629,
;;                                      :headers {:author "Antoine R. Dumont <eniotna.t AT gmail.com>", :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>", :version "0.2.5", :package-requires "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\") (kv \"0.0.17\"))", :keywords "org-mode trello sync org-trello", :url "https://github.com/ardumont/org-trello"},
;;                                      :type "tar",
;;                                      :description "Org minor mode to synchronize with trello"}],
;;                          :created 1373560695}})

(def versions ^{:doc "List of current org-trello version"}
  (for [medium (range 0 3)
        minor (range 0 10)
        :let [version (format "0.%s.%s" medium minor)]
        :when (not (#{"0.0.0" "0.0.1" "0.0.2" "0.2.9"} version))]
    version))

(defn download-by-version [versions] "Given a list of versions, compute the download ratio per versions"
  (->> versions
       (reduce (fn [map version]
                 (let [info-version (-> "org-trello"
                                        (package version)
                                        q/execute)]
                   (assoc map version (get-in info-version [:package :versions 0 :downloads] 0))))
               (sorted-map))))

;; (download-by-version versions)

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

(use '(incanter core stats charts))

(defn barchart-by-versions [versions] ""
  (let [versions (map (comp #(clojure.string/replace % "." "") first) actual-result)
        values   (map second actual-result)]
    (view (bar-chart versions values))))

(barchart-by-versions actual-result)
