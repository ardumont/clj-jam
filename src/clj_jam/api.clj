(ns clj-jam.api
  "Marmalade API abstraction."
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

(defn versions [package-name] "Given a package, extract all its versions information."
  (-> package-name
      package
      q/execute))

(defn downloads-by-version [versions] "Given a list of metadata versions (including version and downloads), compute the download ratio per versions."
  (reduce (fn [m pack-version] (assoc m ((comp #(clojure.string/join "" %) :version) pack-version) (:downloads pack-version)))
          (sorted-map)
          (get-in versions [:package :versions])))

(-> "org-trello"
    versions
    downloads-by-version)
