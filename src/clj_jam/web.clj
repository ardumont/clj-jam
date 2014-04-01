(ns clj-jam.web "A simple api to compute an emacs-lisp package's downloads-per-version chart on marmalage repository"
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.util.response :as resp]
            [ring.middleware.stacktrace :as trace]
            [ring.middleware.session :as session]
            [ring.middleware.session.cookie :as cookie]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.basic-authentication :as basic]
            [cemerick.drawbridge :as drawbridge]
            [environ.core :refer [env]]
            [clj-jam.api :as a]
            [clj-jam.chart :as c]
            [clj-jam.page :as p]))

(defn- authenticated? [user pass]
  ;; TODO: heroku config:add REPL_USER=[...] REPL_PASSWORD=[...]
  (= [user pass] [(env :repl-user false) (env :repl-password false)]))

(def ^:private drawbridge
  (-> (drawbridge/ring-handler)
      (session/wrap-session)
      (basic/wrap-basic-authentication authenticated?)))

(defn response [content-type body] "Generic get response"
  (-> (resp/response body)
      (resp/content-type content-type)))

(defroutes app
  (ANY "/repl" {:as req}
       (drawbridge req))

  (GET "/packages/:pack" [pack]
       (->> pack
            a/versions
            a/downloads-per-version
            pr-str
            (response "text/plain")))

  (GET "/packages/:pack/:start-version" [pack start-version]
       (->> pack
            a/versions
            a/downloads-per-version
            (a/filter-versions start-version)
            pr-str
            (response "text/plain")))

  (GET "/charts/:pack" [pack]
       (->> pack
            a/versions
            a/downloads-per-version
            c/barchart-by-versions
            c/gen-chart-png-outputstream!
            (response "image/png")))

  (GET "/charts/:pack/:start-version" [pack start-version]
       (->> pack
            a/versions
            a/downloads-per-version
            (a/filter-versions start-version)
            c/barchart-by-versions
            c/gen-chart-png-outputstream!
            (response "image/png")))

  (GET "/" []
       (->> (p/home-page)
            (response "text/html")))

  (route/resources "/")

  (ANY "*" []
       (->> "404.html"
            io/resource
            slurp
            route/not-found)))

(defn wrap-error-page [handler]
  (fn [req]
    (try (handler req)
         (catch Exception e
           {:status 500
            :headers {"Content-Type" "text/html"}
            :body (slurp (io/resource "500.html"))}))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))
        ;; TODO: heroku config:add SESSION_SECRET=$RANDOM_16_CHARS
        store (cookie/cookie-store {:key (env :session-secret)})]
    (jetty/run-jetty (-> #'app
                         ((if (env :production)
                            wrap-error-page
                            trace/wrap-stacktrace))
                         (site {:session {:store store}}))
                     {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
