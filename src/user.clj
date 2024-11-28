(ns user
  (:require [vouch-nlp.core :as core]
            [nextjournal.clerk :as clerk]))


(defn start []
  (clerk/serve! {:watch-paths ["notebooks" "src"]})
  (clerk/show! "notebooks/nlp_analysis.clj"))

