(ns vouch-nlp.analysis)

(defn term-frequency
  "Calculates the term frequency for all words in a tokenized document.

   Parameters:
     tokenized-doc - A sequence of words representing a tokenized document.

   Returns:
     A map where keys are words and values are their term frequencies."
  [tokenized-doc]
  (let [word-counts (frequencies tokenized-doc)
        total-words (count tokenized-doc)]
    (->> (for [[word count] word-counts]
           [word (-> (/ count total-words) float)])
         (into {}))))



