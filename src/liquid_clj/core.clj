(ns liquid-clj.core
  (:gen-class)
  (:require [zweikopf.core :refer [init-ruby-context]]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (z/init-ruby-context)
  (z/require "liquid")

  #_(prn (z/ruby-eval "Liquid::Template.parse(\"hi {{name}}\").render('name' => 'world')"))
  (-> "Liquid::Template"
      z/ruby-eval
      (z/call-ruby :parse "hi {{name}}")
      (z/call-ruby :render (z/rubyize {"name" "b"}))
      prn)

  (println "Hello, World!"))
