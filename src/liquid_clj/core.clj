(ns liquid-clj.core
  (:gen-class)
  (:require [zweikopf.core :refer [init-ruby-context]]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (z/init-ruby-context)
  (z/require "liquid")
  (prn (z/ruby-eval "Liquid::Template.parse(\"hi {{name}}\").render('name' => 'world')"))
  (println "Hello, World!"))
