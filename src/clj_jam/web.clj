(ns clj-jam.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.middleware.stacktrace :as trace]
            [ring.middleware.session :as session]
            [ring.middleware.session.cookie :as cookie]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.basic-authentication :as basic]
            [cemerick.drawbridge :as drawbridge]
            [environ.core :refer [env]]
            [clj-jam.api :as a]
            [clj-jam.chart :as c]))

(defn- authenticated? [user pass]
  ;; TODO: heroku config:add REPL_USER=[...] REPL_PASSWORD=[...]
  (= [user pass] [(env :repl-user false) (env :repl-password false)]))

(def ^:private drawbridge
  (-> (drawbridge/ring-handler)
      (session/wrap-session)
      (basic/wrap-basic-authentication authenticated?)))

(defn gen-chart-png [chart] "Given a chart, compute the png equivalent."
  {:status 200
   :headers {"Content-Type" "image/png"}
   :body (c/gen-chart-png-outputstream chart)})

(defroutes app
  (ANY "/repl" {:as req}
       (drawbridge req))
  (GET "/packages/:pack" [pack]
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (-> pack
                  a/versions
                  a/downloads-by-version
                  pr-str)})

  (GET "/chart/:pack" [pack :as req]
       (-> pack
           a/versions
           a/downloads-by-version
           c/barchart-by-versions
           gen-chart-png))

  (GET "/" []
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body "Compute download by version chart for a given emacs-lisp package on marmalade."})
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

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
