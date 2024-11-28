(ns vouch-nlp.visualization
  "Visualization namespace for vouch-nlp analysis results.
   Provides functions to create various charts and plots
   to visually represent NLP analysis outcomes, such as
   word frequency distributions and other text statistics."
  (:require [nextjournal.clerk :as clerk]))

(defn plot-word-frequency [word-freq]
  (let [sorted-freq (sort-by second > word-freq)
        top-words (take 50 sorted-freq)
        words (map first top-words)
        freqs (map second top-words)]
    (println (zipmap words freqs))
    (clerk/plotly
     {:data [{:x words
              :y freqs
              :type "bar"}]
      :layout {:title "Word Frequency"
               :xaxis {:title "Words"
                       :tickangle 45}
               :yaxis {:title "Frequency"}}})))


(defn plot-word-cloud [word-cloud-data]
  (clerk/vl
   {:width 800
    :height 600
    :data {:values (map (fn [[word freq]] {:word word :freq freq}) word-cloud-data)}
    :mark {:type "text" :baseline "middle"}
    :encoding {:x {:field "word" :type "nominal"}
               :y {:field "freq" :type "quantitative"}
               :size {:field "freq" :type "quantitative" :scale {:range [10 100]}}
               :text {:field "word" :type "nominal"}}}))
