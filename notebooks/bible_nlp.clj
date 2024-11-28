(ns bible-nlp
  (:require [nextjournal.clerk :as clerk]
            [vouch-nlp.core :as nlp]
            [vouch-nlp.preprocessing :as prep]
            [vouch-nlp.visualization :as viz]
            [clojure.java.io :as io]
            [vouch-nlp.analysis :as analysis]))

(def genesis-1 (slurp "data/bible-books/Genesis/1.txt"))

(def books (-> (slurp "data/bible-books.edn") read-string))

(def gen-1-analysis (nlp/analyze-text genesis-1))

(def genesis (-> (slurp "data/bible-books/Genesis/chapters.edn") read-string))

(def genesis-text (->> (map :text genesis)
                       (clojure.string/join " ")))

(def genesis-text-analysis (nlp/analyze-text genesis-text))


(viz/plot-word-frequency (analysis/term-frequency (-> (:tokens genesis-text-analysis) prep/remove-stop-words)))

#_(viz/plot-word-cloud (take 50 ( (-> (:tokens genesis-text-analysis) prep/remove-stop-words))))



