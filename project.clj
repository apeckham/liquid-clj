(defproject liquid-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [zweikopf "1.0.2"]
                 [hiccup "1.0.5"]]
  :main ^:skip-aot liquid-clj.core
  :target-path "target/%s"
  :plugins [[com.jakemccrary/lein-test-refresh "0.21.1"]]
  :profiles {:uberjar {:aot :all}})
