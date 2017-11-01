(ns liquid-clj.core-test
  (:require [clojure.test :refer :all]
            [liquid-clj.core :refer :all]
            [zweikopf.core :as z]
            [hiccup.core :refer [html]]))

(z/init-ruby-context)
(z/ruby-require "liquid")

(defn liq-var [v]
  (str "{{ " (name v) " }}"))

(defn liq-for [v coll children]
  (list (format "{%% for %s in %s %%}" (name v) (name coll))
        children
        "{% endfor %}"))

(defn liq-if [condition children]
  (list (format "{%% if %s %%}" condition)
        children
        "{% endif %}"))

(defn liq-raw [children]
  (list "{% raw %}" children "{% endraw %}"))

(defn render [template data]
  (-> (z/ruby-eval "Liquid::Template")
      (z/call-ruby :parse template)
      (z/call-ruby :render (z/rubyize data)))) 
(deftest html-test
  (is (= "<span class=\"foo\">Hello {{ name }}</span>"
         (html [:span {:class "foo"} "Hello " (liq-var :name)]))))

(deftest eval-test
  (is (= "hi world"
         (z/ruby-eval "Liquid::Template.parse(\"hi {{name}}\").render('name' => 'world')"))))

(deftest render-test
  (is (= "hello world!" (render "hello {{name}}!" {"name" "world"}))))

(deftest together-test
  (let [template [:span "hello " (liq-var :name) "!"]]
    (is (= "<span>hello world!</span>"
           (render (html template) {"name" "world"})))))

(deftest for-test
  (let [template [:ul (liq-for :product :products
                               [:li (liq-var :product.name)])]]
    (is (= "<ul>{% for product in products %}<li>{{ product.name }}</li>{% endfor %}</ul>"
           (html template)))
    (is (= "<ul><li>one</li><li>two</li></ul>"
           (render (html template) {"products" [{"name" "one"}
                                                {"name" "two"}]})))))

(deftest if-test
  (let [template (html (liq-if "number == 1"
                               [:b "cool"]))]
    (is (= "{% if number == 1 %}<b>cool</b>{% endif %}" template))
    (is (= "<b>cool</b>" (render template {"number" 1})))))

(deftest raw-test
  (let [template (html (liq-raw "{{curlies}}"))]
    (is (= "{{curlies}}" (render template {})))))
