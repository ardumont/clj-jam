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
        {}
        (get-in versions [:package :versions])))

(-> "org-trello"
    versions
    downloads-by-version)

(def tmp-result {:message "Got org-trello",
 :package
 {:downloads 366,
  :name "org-trello",
  :owners {:ardumont "eniotna.t@gmail.com"},
  :versions
  [{:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]
     ["kv" [0 0 17]]],
    :commentary nil,
    :name "org-trello",
    :version [0 2 8],
    :downloads 16,
    :created 1388940730,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.2.8",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\") (kv \"0.0.17\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8
 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]
     ["kv" [0 0 17]]],
    :commentary nil,
    :name "org-trello",
    :version [0 2 7],
    :downloads 2,
    :created 1388861640,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.2.7",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\") (kv \"0.0.17\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7
0]]
     ["kv" [0 0 17]]],
    :commentary nil,
    :name "org-trello",
    :version [0 2 6],
    :downloads 46,
    :created 1385330099,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.2.6",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\") (kv \"0.0.17\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]
     ["kv" [0 0 17]]],
    :commentary nil,
    :name "org-trello",
    :version [0 2 5],
    :downloads 3,
    :created 1385249629,
    :headers
    {:author
"Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.2.5",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\") (kv \"0.0.17\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]
     ["kv" [0 0 17]]],
    :commentary nil,
    :name "org-trello",
    :version [0 2 4],
    :downloads 1,
    :created 1385229436,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.2.4",
     :package-requires
     "((o
rg \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\") (kv \"0.0.17\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]
     ["kv" [0 0 17]]],
    :commentary nil,
    :name "org-trello",
    :version [0 2 3],
    :downloads 14,
    :created 1384811863,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.2.3",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\") (kv \"0.0.17\"))",

:keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]
     ["kv" [0 0 17]]],
    :commentary nil,
    :name "org-trello",
    :version [0 2 2],
    :downloads 57,
    :created 1380580588,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.2.2",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\") (kv \"0.0.17\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize
with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]],
    :commentary nil,
    :name "org-trello",
    :version [0 2 1 2],
    :downloads 34,
    :created 1379611415,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.2.1.2",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]

["s" [1 7 0]]],
    :commentary nil,
    :name "org-trello",
    :version [0 2 1 1],
    :downloads 1,
    :created 1379533700,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.2.1.1",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:name "org-trello",
    :description "Org minor mode to synchronize with trello",
    :requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]],
    :version [0 2 1],
    :type "tar",
    :headers
    {:author "Antoine R. Dumont <eniotna.t
AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.2.1",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :commentary nil,
    :created 1379533356}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]],
    :commentary nil,
    :name "org-trello",
    :version [0 2 0],
    :downloads 20,
    :created 1378929544,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.2.0",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.
9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]],
    :commentary nil,
    :name "org-trello",
    :version [0 1 9],
    :downloads 13,
    :created 1378410245,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.1.9",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to
synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]],
    :commentary nil,
    :name "org-trello",
    :version [0 1 8],
    :downloads 25,
    :created 1378157423,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.1.8",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0
]]
     ["s" [1 7 0]]],
    :commentary nil,
    :name "org-trello",
    :version [0 1 7 1],
    :downloads 4,
    :created 1378033524,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.1.7.1",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]],
    :commentary nil,
    :name "org-trello",
    :version [0 1 7],
    :downloads 2,
    :created 1377955910,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmai
l.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.1.7",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]
     ["s" [1 7 0]]],
    :commentary nil,
    :name "org-trello",
    :version [0 1 6],
    :downloads 10,
    :created 1377689015,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.1.6",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (js
on \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\") (s \"1.7.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]],
    :commentary nil,
    :name "org-trello",
    :version [0 1 5],
    :downloads 5,
    :created 1377439387,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.1.5",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchron
ize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]],
    :commentary nil,
    :name "org-trello",
    :version [0 1 4],
    :downloads 2,
    :created 1377363109,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.1.4",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]
     ["esxml" [0 3 0]]],
    :commentary nil,
    :name "org-t
rello",
    :version [0 1 3],
    :downloads 10,
    :created 1377246428,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.1.3",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]
     ["elnode" [0 9 9 7 6]]],
    :commentary nil,
    :name "org-trello",
    :version [0 1 2],
    :downloads 8,
    :created 1377011867,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.1.2",
     :package-requires

"((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8 0 7]]
     ["dash" [1 5 0]]
     ["request" [0 2 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]],
    :commentary nil,
    :name "org-trello",
    :version [0 1 1],
    :downloads 8,
    :created 1376222155,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.1.1",
     :package-requires
     "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Org minor mode to synchronize with trello"}
   {:requires
    [["org" [8
0 7]]
     ["dash" [1 4 0]]
     ["request" [0 1 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]],
    :commentary nil,
    :name "org-trello",
    :version [0 1 0],
    :downloads 16,
    :created 1375880845,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.1.0",
     :package-requires
     "((org \"8.0.7\") (dash \"1.4.0\") (request \"0.1.0\") (cl-lib \"0.3.0\") (json \"1.2\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Minor mode for org-mode to sync org-mode and trello"}
   {:requires
    [["org" [7 9 2]]
     ["dash" [1 4 0]]
     ["request" [0 1 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]],
    :commentary nil,
    :name "org-trello",
    :version [0 0 9],
    :downloads 5,
    :created 1375801038,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumon
t <eniotna.t AT gmail.com>",
     :version "0.0.9",
     :package-requires
     "((org \"7.9.2\") (dash \"1.4.0\") (request \"0.1.0\") (cl-lib \"0.3.0\") (json \"1.2\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Minor mode for org-mode to sync org-mode and trello"}
   {:requires
    [["org" [7 9 2]]
     ["dash" [1 4 0]]
     ["request" [0 1 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]],
    :commentary nil,
    :name "org-trello",
    :version [0 0 8],
    :downloads 20,
    :created 1375231191,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.0.8",
     :package-requires
     "((org \"7.9.2\") (dash \"1.4.0\") (request \"0.1.0\") (cl-lib \"0.3.0\") (json \"1.2\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Minor
 mode for org-mode to sync org-mode and trello"}
   {:requires
    [["org" [7 9 2]]
     ["dash" [1 4 0]]
     ["request" [0 1 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]],
    :commentary nil,
    :name "org-trello",
    :version [0 0 7],
    :downloads 13,
    :created 1374778339,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.0.7",
     :package-requires
     "((org \"7.9.2\") (dash \"1.4.0\") (request \"0.1.0\") (cl-lib \"0.3.0\") (json \"1.2\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Minor mode for org-mode to sync org-mode and trello"}
   {:requires
    [["org" [7 9 2]]
     ["dash" [1 4 0]]
     ["request" [0 1 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 2]]],
    :commentary nil,
    :name "org-trello",
    :version [0 0 6 1],
    :downloads 1,
    :created 1374742805,
    :headers
    {:author
"Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.0.6.1",
     :package-requires
     "((org \"7.9.2\") (dash \"1.4.0\") (request \"0.1.0\") (cl-lib \"0.3.0\") (json \"1.2\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Minor mode for org-mode to sync org-mode and trello"}
   {:requires
    [["org" [7 9 2]]
     ["dash" [1 4 0]]
     ["request" [0 1 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 3]]],
    :commentary nil,
    :name "org-trello",
    :version [0 0 6],
    :downloads 1,
    :created 1374694542,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.0.6",
     :package-requires
     "((org \"7.9.2\") (dash \"1.4.0\") (request \"0.1.0\") (cl-lib \"0.3.0\") (json \"1.3\"))",
     :keywords "org-mode trello sync org-trello",
     :url "h
ttps://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Minor mode for org-mode to sync org-mode and trello"}
   {:requires
    [["org" [7 9 2]]
     ["dash" [1 4 0]]
     ["request" [0 1 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 3]]],
    :commentary nil,
    :name "org-trello",
    :version [0 0 5],
    :downloads 10,
    :created 1374157555,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.0.5",
     :package-requires
     "((org \"7.9.2\") (dash \"1.4.0\") (request \"0.1.0\") (cl-lib \"0.3.0\") (json \"1.3\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Minor mode for org-mode to sync org-mode and trello"}
   {:requires
    [["org" [7 9 2]]
     ["dash" [1 4 0]]
     ["request" [0 1 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 3]]],
    :commentary nil,
    :name "org-trello",
    :version
 [0 0 4 1],
    :downloads 8,
    :created 1374000521,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.0.4.1",
     :package-requires
     "((org \"7.9.2\") (dash \"1.4.0\") (request \"0.1.0\") (cl-lib \"0.3.0\") (json \"1.3\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Minor mode for org-mode to sync org-mode and trello"}
   {:requires
    [["org" [7 9 2]]
     ["dash" [1 4 0]]
     ["request" [0 1 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 3]]],
    :commentary nil,
    :name "org-trello",
    :version [0 0 4],
    :downloads 1,
    :created 1373993023,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.0.4",
     :package-requires
     "((org \"7.9.2\") (dash \"1.4.0\") (request \"0.1.0\") (cl-lib \"0.3.0\")
 (json \"1.3\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Minor mode for org-mode to sync org-mode and trello"}
   {:requires
    [["org" [7 9 2]]
     ["dash" [1 4 0]]
     ["request" [0 1 0]]
     ["cl-lib" [0 3 0]]
     ["json" [1 3]]],
    :commentary nil,
    :name "org-trello",
    :version [0 0 3 1],
    :downloads 8,
    :created 1373655363,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.0.3.1",
     :package-requires
     "((org \"7.9.2\") (dash \"1.4.0\") (request \"0.1.0\") (cl-lib \"0.3.0\") (json \"1.3\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description "Minor mode for org-mode to sync org-mode and trello"}
   {:requires
    [["org" [7 9 2]]
     ["dash" [1 4 0]]
     ["request" [0 1 0]]
     ["cl-lib" [0 3
 0]]
     ["json" [1 3]]],
    :commentary nil,
    :name "org-trello",
    :version [0 0 3],
    :downloads 2,
    :created 1373560695,
    :headers
    {:author "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>",
     :version "0.0.3",
     :package-requires
     "((org \"7.9.2\") (dash \"1.4.0\") (request \"0.1.0\") (cl-lib \"0.3.0\") (json \"1.3\"))",
     :keywords "org-mode trello sync org-trello",
     :url "https://github.com/ardumont/org-trello"},
    :type "tar",
    :description
    "Minor mode for org-mode to sync org-mode and trello"}],
:created 1373560695}})

(reduce (fn [m pack-version] (assoc m ((comp #(clojure.string/join "" %) :version) pack-version) (:downloads pack-version)))
        {}
        (get-in tmp-result [:package :versions]))
