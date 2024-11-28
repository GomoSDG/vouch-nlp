(ns vouch-nlp.core
  "Core namespace for vouch-nlp analysis.
   Provides main functions for text analysis and integrates
   preprocessing, analysis, and visualization components.
   Acts as the primary entry point for the NLP pipeline."
  (:require [nextjournal.clerk :as clerk]
            [vouch-nlp.preprocessing :as prep]
            [vouch-nlp.visualization :as viz]
            [opennlp.nlp :as nlp]
            [clojure.string :as str]))

(def pos-abbreviations
  {"ADJ"  "Adjective"
   "ADP"  "Adposition"
   "ADV"  "Adverb"
   "AUX"  "Auxiliary Verb"
   "CONJ" "Coordinating Conjunction"
   "DET"  "Determiner"
   "INTJ" "Interjection"
   "NOUN" "Noun"
   "NUM"  "Numeral"
   "PART" "Particle"
   "PRON" "Pronoun"
   "PROPN" "Proper Noun"
   "PUNCT" "Punctuation"
   "SCONJ" "Subordinating Conjunction"
   "SYM"  "Symbol"
   "VERB" "Verb"
   "X"    "Other"

   ; Additional common abbreviations
   "CC"   "Coordinating Conjunction"
   "CD"   "Cardinal Number"
   "DT"   "Determiner"
   "EX"   "Existential There"
   "FW"   "Foreign Word"
   "IN"   "Preposition or Subordinating Conjunction"
   "JJ"   "Adjective"
   "JJR"  "Adjective, Comparative"
   "JJS"  "Adjective, Superlative"
   "LS"   "List Item Marker"
   "MD"   "Modal"
   "NN"   "Noun, Singular or Mass"
   "NNS"  "Noun, Plural"
   "NNP"  "Proper Noun, Singular"
   "NNPS" "Proper Noun, Plural"
   "PDT"  "Predeterminer"
   "POS"  "Possessive Ending"
   "PRP"  "Personal Pronoun"
   "PRP$" "Possessive Pronoun"
   "RB"   "Adverb"
   "RBR"  "Adverb, Comparative"
   "RBS"  "Adverb, Superlative"
   "RP"   "Particle"
   "TO"   "to"
   "UH"   "Interjection"
   "VB"   "Verb, Base Form"
   "VBD"  "Verb, Past Tense"
   "VBG"  "Verb, Gerund or Present Participle"
   "VBN"  "Verb, Past Participle"
   "VBP"  "Verb, Non-3rd Person Singular Present"
   "VBZ"  "Verb, 3rd Person Singular Present"
   "WDT"  "Wh-determiner"
   "WP"   "Wh-pronoun"
   "WP$"  "Possessive Wh-pronoun"
   "WRB"  "Wh-adverb"})

(def tokenize (nlp/make-tokenizer "models/en-token.bin"))
(def pos-tag (nlp/make-pos-tagger "models/en-pos-maxent.bin"))
(def get-sentences (nlp/make-sentence-detector "models/en-sent.bin"))

(defn ->friendly-pos-tags [tagged]
  (map (fn [[word tag]]
         [word (get pos-abbreviations tag tag)])
       tagged))

(def ambiguous-char-map
  {\u00A0 \space  ; non-breaking space to regular space
   \u2013 \-      ; en dash to hyphen
   \u2014 \-      ; em dash to hyphen
   \u2018 \'      ; left single quote to apostrophe
   \u2019 \'      ; right single quote to apostrophe
   \u201C \"      ; left double quote to straight quote
   \u201D \"      ; right double quote to straight quote
   \u2026 "..."   ; ellipsis to three dots
   })

(defn replace-ambiguous-chars
  "Replace ambiguous characters in a string with their unambiguous counterparts."
  [s]
  (str/escape s ambiguous-char-map))

(defn analyze-text [text]
  (let [tokens (map str/lower-case (-> (replace-ambiguous-chars text) (tokenize)))
        tagged (pos-tag tokens)
        sentences (get-sentences text)]
    {:tokens tokens
     :pos-tags tagged
     :word-count (count tokens)
     :sentence-count (count sentences)}))

(defn run-analysis [input-file]
  (let [text (slurp input-file)
        analysis-result (analyze-text text)]
    (clerk/table analysis-result)
    (viz/plot-word-frequency (prep/word-frequency (:tokens analysis-result)))))

(comment
  (clerk/serve! {:watch-paths ["notebooks" "src"]})
  (clerk/show! "notebooks/nlp_analysis.clj"))
