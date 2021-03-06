(ns clj-jam.scratch
  "Query the marmalade api (the basic authentication scheme is implemented here)"
  (:require [clj-jam.query  :as q]
            [clojure.string :as s]))

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


;; (q/execute (package "org-trello" "latest"))

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

(defn release-package "Release a package to marmalade"
  [name token file]
  {:method :post
   :uri    (format "/v1/packages?name=%s" name)
;;   :content-type "multiform/form-data"
   :headers {"Content-Disposition" "multiform/form-data"}
   :multipart [{:name "Content/type" :content "application/tar"}
               {:name "name"    :content name}
               {:name "token"   :content token}
               {:name "package" :content (clojure.java.io/file file)
                ;; :mime-type "application/tar"
                ;; :encoding "UTF-8"
                }]})

;; (release-package "org-trello" "some-token" "some-file")

(comment
  (-> (release-package "ardumont" q/marmalade-creds "/home/tony/repo/perso/org-trello/org-trello-0.1.5.tar")
      q/execute)
  )
