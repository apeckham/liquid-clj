(ns liquid-clj.core
  (:gen-class)
  (:require [zweikopf.core :as z]
            [hiccup.core :refer [html]]))

(defn liquid-var [v]
  (str "{{ " (name v) " }}"))

(defn -main
  [& args]
  (z/init-ruby-context)
  (z/ruby-require "liquid")

  #_(-> "Liquid::Template.parse(\"hi {{name}}\").render('name' => 'world')"
      z/ruby-eval
      prn)

  (let [template (html [:span {:class "foo"} "Hello " (liquid-var :name)])]
    (-> "Liquid::Template"
        z/ruby-eval
        (z/call-ruby :parse template)
        (z/call-ruby :render (z/rubyize {"name" "world!"}))
        prn)))
