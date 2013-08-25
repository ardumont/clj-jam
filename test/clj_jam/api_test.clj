(ns clj-jam.test.api-test
  (:use [clj-jam.api]
        [expectations]))

(expect {:method :get
         :uri "/v1/users/ardumont"}
        (user "ardumont"))

(expect {:method :post
         :uri "/v1/users/login?name=ardumont&password=some-dummy-pass"}
        (token "ardumont" "some-dummy-pass"))

(expect {:method :get
         :uri "/v1/packages/org-trello"}
        (package "org-trello"))

(expect {:method :get
         :uri "/v1/packages/org-trello/0.1.5"}
        (package "org-trello" "0.1.5"))
