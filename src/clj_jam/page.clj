(ns clj-jam.page "Reference page content we want to display"
    (:use [hiccup core page]))

(defn- header [title]
  "A simple html header where you can override the title"
  [:head
      [:title title]
      (include-css "/css/style.css")])

(defn index-page []
  "The home page"
  (html5
    (header "clj-jam - chart your emacs package's downloads by version")
    [:body
      [:p "Compute downloads by version for a given emacs package. You can chart or simply display result."]
      [:ul
       [:li "/charts/:package-name to draw the chart for the package with name :package-name"]
       [:li "/packages/:package-name to access the downloads-per-version for the same package"]]

     [:p "Sample"]
      [:ul
       [:li [:a {:href "/charts/org-trello"} "org-trello chart"]]
       [:li [:a {:href "/packages/org-trello"} "org-trello downloads-per-version"]]]]))
