(ns liquid-clj.core-test
  (:require [clojure.test :refer :all]
            [liquid-clj.core :refer :all]
            [zweikopf.core :as z]
            [hiccup.core :refer [html]]))

(z/init-ruby-context)
(z/ruby-require "liquid")

#_(defn -main
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

(defn render [template data]
  (-> "Liquid::Template"
      z/ruby-eval
      (z/call-ruby :parse template)
      (z/call-ruby :render (z/rubyize data))))

(deftest render-test
  (is (= "hello world!" (render "hello {{name}}!" {"name" "world"}))))
