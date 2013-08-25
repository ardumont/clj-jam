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
