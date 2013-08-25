(ns clj-jam.api
  "Query the marmalade api (the basic authentication scheme is implemented here)"
  (:require [clj-jam.query  :as q]
            [clojure.string :as s]))

(q/api :get "/v1/users/ardumont")
;;{:message "Got ardumont", :user {:name "ardumont", :packages ["org-trello"], :created 1372878705}}

(defn retrieve-token [user pass]
  (q/api :post (format "/v1/users/login?name=%s&password=%s" user pass)))

;; (api :post "/v1/users/login?name=username&password=yourpass")
;; {:message "Logged in as \"username\"", :name "username", :token "some-token"}

;; to retrieve the token, you are forced to use the api
;; (retrieve-token "username" "yourpass")
;; {:message "Logged in as \"username\"", :name "username", :token "some-token"}

(q/api :get "/v1/packages/org-trello/0.1.5")

;; {:message "Got org-trello
;;  version 0.1.5"
;;  :package {:downloads 109
;;            :name "org-trello"
;;            :owners {:ardumont "eniotna.t@gmail.com"}
;;            :versions [{:requires [["org" [8 0 7]]
;;                                   ["dash" [1 5 0]]
;;                                   ["request" [0 2 0]]
;;                                   ["cl-lib" [0 3 0]]
;;                                   ["json" [1 2]]
;;                                   ["elnode" [0 9 9 7 6]]
;;                                   ["esxml" [0 3 0]]]
;;                        :commentary nil
;;                        :name "org-trello"
;;                        :version [0 1 5]
;;                        :downloads 1
;;                        :created 1377439387
;;                        :headers {:author "Antoine R. Dumont <eniotna.t AT gmail.com>"
;;                                  :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>"
;;                                  :version "0.1.5"
;;                                  :package-requires "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\"))"
;;                                  :keywords "org-mode trello sync org-trello"
;;                                  :url "https://github.com/ardumont/org-trello"}
;;                        :type "tar"
;;                        :description "Org minor mode to synchronize with trello"}]
;;            :created 1373560695}}


(q/api :get "/v1/packages/org-trello/latest")

;; {:message "Got org-trello"
;;  :package {:downloads 109
;;            :name "org-trello"
;;            :owners {:ardumont "eniotna.t@gmail.com"}
;;            :versions [{:name "org-trello"
;;                        :description "Org minor mode to synchronize with trello"
;;                        :requires [["org" [8 0 7]]
;;                                   ["dash" [1 5 0]]
;;                                   ["request" [0 2 0]]
;;                                   ["cl-lib" [0 3 0]]
;;                                   ["json" [1 2]]
;;                                   ["elnode" [0 9 9 7 6]]
;;                                   ["esxml" [0 3 0]]]
;;                        :version [0 1 5]
;;                        :type "tar"
;;                        :headers {:author "Antoine R. Dumont <eniotna.t AT gmail.com>"
;;                                  :maintainer "Antoine R. Dumont <eniotna.t AT gmail.com>"
;;                                  :version "0.1.5"
;;                                  :package-requires "((org \"8.0.7\") (dash \"1.5.0\") (request \"0.2.0\") (cl-lib \"0.3.0\") (json \"1.2\") (elnode \"0.9.9.7.6\") (esxml \"0.3.0\"))"
;;                                  :keywords "org-mode trello sync org-trello"
;;                                  :url "https://github.com/ardumont/org-trello"}
;;                        :commentary nil
;;                        :created 1377439387}]
;;            :created 1373560695}}
