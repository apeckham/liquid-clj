(ns liquid-clj.core
  (:gen-class)
  (:require [zweikopf.core :as z]))

(defn -main
  [& args]
  (z/init-ruby-context)
  (z/require "liquid")

  #_(-> "Liquid::Template.parse(\"hi {{name}}\").render('name' => 'world')"
      z/ruby-eval
      prn)

  (-> "Liquid::Template"
      z/ruby-eval
      (z/call-ruby :parse "hi {{name}}")
      (z/call-ruby :render (z/rubyize {"name" "b"}))
      prn))
