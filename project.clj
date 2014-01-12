(defproject clj-jam "0.1.0-SNAPSHOT"
  :description "Exploration of emacs-lisp's repository marmalade api."
  :url "http://infinite-citadel-3625.herokuapp.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-http            "0.7.8"]
                 [org.clojure/clojure "1.5.1"]
                 [incanter            "1.5.4"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [compojure "1.1.1"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [ring/ring-devel "1.1.0"]
                 [ring-basic-authentication "1.0.1"]
                 [environ "0.2.1"]
                 [com.cemerick/drawbridge "0.0.6"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]
            [lein-expectations    "0.0.7"]]
  :hooks [environ.leiningen.hooks]
  :profiles {:dev {:dependencies [[expectations "1.4.53"]]}
             :production {:env {:production true}}})
