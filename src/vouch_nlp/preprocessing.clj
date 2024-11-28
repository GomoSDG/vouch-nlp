(ns vouch-nlp.preprocessing
  "Preprocessing namespace for vouch-nlp tasks.
   Provides functions for text cleaning and normalization,
   including lowercasing, punctuation removal, tokenization,
   and stop word removal. These functions prepare raw text
   for further NLP analysis."
  (:require [clojure.string :as str]))

(def stop-words (->> (slurp "stopwords.txt")
                     (str/split-lines)
                     (set)))

(defn remove-punctuation [text]
  (str/replace text #"[^\w\s]" ""))

(defn lowercase [text]
  (str/lower-case text))

(defn remove-stop-words [tokens]
  (remove stop-words tokens))

(defn word-frequency [tokens]
  (frequencies tokens))

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

(defn preprocess [text]
  (-> text
      lowercase
      remove-punctuation
      (str/split #"\s+")
      remove-stop-words))

