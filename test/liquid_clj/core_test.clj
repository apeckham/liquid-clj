(ns liquid-clj.core-test
  (:require [clojure.test :refer :all]
            [liquid-clj.core :refer :all]
            [zweikopf.core :as z]
            [hiccup.core :refer [html]]))

(z/init-ruby-context)
(z/ruby-require "liquid")

(defn liq-var [v]
  (str "{{ " (name v) " }}"))

(deftest html-test
  (is (= "<span class=\"foo\">Hello {{ name }}</span>"
         (html [:span {:class "foo"} "Hello " (liq-var :name)]))))

(deftest eval-test
  (is (= "hi world"
         (z/ruby-eval "Liquid::Template.parse(\"hi {{name}}\").render('name' => 'world')"))))

(defn render [template data]
  (-> (z/ruby-eval "Liquid::Template")
      (z/call-ruby :parse template)
      (z/call-ruby :render (z/rubyize data))))

(deftest render-test
  (is (= "hello world!" (render "hello {{name}}!" {"name" "world"}))))

(deftest together-test
  (let [template [:span "hello " (liq-var :name) "!"]]
    (is (= "<span>hello world!</span>"
           (render (html template) {"name" "world"})))))
