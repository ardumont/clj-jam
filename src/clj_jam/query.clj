(ns clj-jam.query
  "Query the marmalade api (the basic authentication scheme is implemented here)"
  (:require [clj-http.client   :as c]
            [clojure.string    :as s]))

;; your credentials in the ~/.marmalade/config.clj file
;; (def marmalade-creds "your-token")

;; load consumer-key, consumer-secret-key and access-token that does give access to everything forever (boum)
(load-file (str (System/getProperty "user.home") "/.marmalade/config.clj"))

(def URL "The needed prefix url for marmalade" "http://www.marmalade-repo.org")

(defn compute-url "Compute url with authentication needed."
  ([url path]
     (format "%s%s" url path))
  ([url path secret-token]
     (let [full-url (compute-url url path)]
       (if secret-token
         (format "%s?token=%s" full-url secret-token)
         full-url))))

(defn trace
  [t]
  (println (format "%s" t))
  t)

(defn api
  "Query marmalade using the secret-token provided or use the one loaded from ~/.marmalade/config.clj"
  [method path & [req]]
  (->> {:method     method
        :url        (compute-url URL path (if marmalade-creds marmalade-creds))
        :accept     :json
        :as         :json}
       (merge req)
       c/request
       trace
       :body))
