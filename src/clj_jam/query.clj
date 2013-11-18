(ns clj-jam.query
  "Query the marmalade api (the basic authentication scheme is implemented here)"
  (:require [clj-http.client :as c]
            [clojure.string  :as s]))

;; your credentials in the ~/.marmalade/config.clj file
;; (def marmalade-creds "your-token")

(def marmalade-creds nil)
;; load consumer-key, consumer-secret-key and access-token that does give access to everything forever (boum)
(load-file (str (System/getProperty "user.home") "/.marmalade/config.clj"))

(def URL "The needed prefix url for marmalade" "http://www.marmalade-repo.org")

(defn compute-parameter "Compute the parameter token in an already formed url."
  [url token]
  (let [n (-> url
              (s/split #"\?")
              count)]
    (format "%s%ctoken=%s" url (if (= 1 n) \? \&) token)))

(defn compute-url "Compute url with authentication needed."
  ([url path]
     (format "%s%s" url path))
  ([url path secret-token]
     (let [full-url (compute-url url path)]
       (if secret-token
         (compute-parameter full-url secret-token)
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
        :as         :json
        :debug      true}
       (merge req)
       c/request
       :body))

(defn- execute-post-or-put "Execute post or put request"
  [fn-post-or-put path body]
  (let [req {:accept :json
             :as     :json}
        full-req (if body
                   (merge {:form-params body :content-type :json} req)
                   req)]
    (-> (compute-url URL path (if marmalade-creds marmalade-creds))
        (fn-post-or-put full-req)
        :body)))

(defn- post "POST" [path body] (execute-post-or-put c/post path body))
(defn- put "PUT" [path body]   (execute-post-or-put c/put path body))

(defn dispatch-execute "Dispatch function to execute the query depending on the content."
  [{:keys [method multipart]}]
  [method (if multipart :multipart nil)])

(defmulti  execute dispatch-execute)
(defmethod execute [:get nil]         [{:keys [uri params]}]    (api :get uri params))
(defmethod execute [:post nil]        [{:keys [uri params]}]    (api :post uri params))
(defmethod execute [:post :multipart] [{:keys [uri multipart] :as req}] (api :post uri (-> req
                                                                                           (dissoc :uri)
                                                                                           (dissoc :params))))
(defmethod execute [:put nil]         [{:keys [uri params]}]    (put uri params))

(comment
  (api :post "/v1/packages"
       {:content-type "multiform/form-data"
        :multipart [{:name "Content/type" :content "application/tar"}
                    {:name "name" :content "org-trello"}
                    {:name "token" :content marmalade-creds}
                    {:name "package" :content (clojure.java.io/file "/home/tony/repo/perso/org-trello/org-trello-0.1.5.tar")}]})

  (c/post (compute-url URL "/v1/packages")
          {
           :headers {"Content-Disposition" "multiform/form-data"}
           ;;:content-type "multiform/form-data"
           :debug true
           ;;         :accept :json
           ;;         :as :json
           :response-interceptor (fn [resp ctx] (println ctx))
           :multipart [{:name "name"    :content "ardumont"}
                       {:name "token"   :content marmalade-creds}
                       {:name "package" :content (slurp (clojure.java.io/file "/home/tony/repo/perso/org-trello/org-trello-dummy.el"))
                        ;;                      :mime-type "text/plain" :encoding "utf-8"
                        }
                       ;;          {:name "Content/type" :content "text/plain" }
                       ]}))
