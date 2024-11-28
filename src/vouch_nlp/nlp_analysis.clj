(ns notebooks.nlp-analysis
  "Clerk notebook for interactive vouch-nlp analysis.
   Demonstrates the use of core NLP functions, preprocessing steps,
   and visualization capabilities. Serves as both documentation
   and an interactive playground for exploring NLP techniques."
  (:require [nextjournal.clerk :as clerk]
            [vouch-nlp.core :as nlp]
            [vouch-nlp.preprocessing :as prep]
            [vouch-nlp.visualization :as viz]))

;; # NLP Analysis Notebook

;; This notebook demonstrates the NLP analysis capabilities of our project.

;; ## Load and Analyze Text

(def sample-text (slurp "data/sample.txt"))

(clerk/md "### Sample Text")
(clerk/md sample-text)

(clerk/md "### Text Analysis")
(def analysis-result (nlp/analyze-text sample-text))
(clerk/table analysis-result)

;; ## Word Frequency Visualization

(clerk/md "### Word Frequency Plot")
(viz/plot-word-frequency (prep/word-frequency (:tokens analysis-result)))

;; ## Part-of-Speech Analysis

(clerk/md "### Part-of-Speech Distribution")
(let [pos-freq (frequencies (map second (:pos-tags analysis-result)))]
  (clerk/table pos-freq))
