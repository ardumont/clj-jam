(ns clj-jam.page "Reference page content we want to display"
    (:use [hiccup core page]))

(defn- header [title]
  "A simple html header where you can override the title"
  [:head
   [:title title]
   (include-css "/css/main.css")
   (include-css "/css/print.css")
   (include-css "/css/pygment_trac.css")
   (include-css "/css/stylesheet.css")
   (include-css "/css/syntax.css")])

(defn page-with-content [body]
  "The home page"
  (html5
    (header "clj-jam - chart your emacs package's downloads per version")
    [:body body]))

(defn home-page []
  "Home page"
  (-> [:div {:class "site"}
       [:div {:class "post"}
        [:h1 "clj-jam"]
        [:div "For a given emacs package on marmalade, compute downloads per version and charts or simply return computation result."]
        [:ul
         [:li "/charts/:package-name"]
         [:li "/packages/:package-name"]]

        [:div "Sample"]
        [:ul
         [:li [:a {:href "/charts/org-trello"} "/charts/org-trello"]]
         [:li [:a {:href "/packages/org-trello"} "/packages/org-trello"]]]]]
      page-with-content))
