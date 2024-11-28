# vouch-nlp: Clojure NLP Analysis Project

vouch-nlp is a framework for Natural Language Processing (NLP) analysis using Clojure and Clerk. It provides basic text preprocessing, analysis, and visualization capabilities.

... (previous sections remain the same)

## OpenNLP Models

### Currently Used Models

1. **Tokenizer (en-token.bin)**
   - Purpose: Breaks text into individual words or tokens.
   - Download: [en-token.bin](https://opennlp.apache.org/models/apache-opennlp-models-2.0.0/opennlp-en-ud-ewt-tokens-2.0.0-1.9.3.bin)

2. **Part-of-Speech Tagger (en-pos-maxent.bin)**
   - Purpose: Assigns grammatical categories to each word in a text.
   - Download: [en-pos-maxent.bin](https://opennlp.apache.org/models/apache-opennlp-models-2.0.0/opennlp-en-ud-ewt-pos-2.0.0-1.9.3.bin)

3. **Sentence Detector (en-sent.bin)**
   - Purpose: Identifies sentence boundaries in a given text.
   - Download: [en-sent.bin](https://opennlp.apache.org/models/apache-opennlp-models-2.0.0/opennlp-en-ud-ewt-sentence-2.0.0-1.9.3.bin)

### Additional Useful OpenNLP Models

Here are other OpenNLP models that can enhance the NLP capabilities of vouch-nlp:

1. **Named Entity Recognition (NER) Models**
   - Purpose: Identifies and classifies named entities in text.
   - Available models:
     - [en-ner-person.bin](https://opennlp.apache.org/models/apache-opennlp-models-2.0.0/opennlp-en-ner-person.bin): Recognizes person names
     - [en-ner-location.bin](https://opennlp.apache.org/models/apache-opennlp-models-2.0.0/opennlp-en-ner-location.bin): Recognizes location names
     - [en-ner-organization.bin](https://opennlp.apache.org/models/apache-opennlp-models-2.0.0/opennlp-en-ner-organization.bin): Recognizes organization names
   - When to use: For extracting specific types of information from text, such as identifying people, places, or organizations mentioned.

2. **Chunker Model (en-chunker.bin)**
   - Purpose: Divides text into syntactically correlated parts of words.
   - Download: [en-chunker.bin](https://opennlp.apache.org/models/apache-opennlp-models-2.0.0/opennlp-en-chunker.bin)
   - When to use: For shallow parsing tasks, identifying constituents in a sentence without specifying their internal structure or role.

3. **Parser Model (en-parser-chunking.bin)**
   - Purpose: Performs syntactic analysis of sentences.
   - Download: [en-parser-chunking.bin](https://opennlp.apache.org/models/apache-opennlp-models-2.0.0/opennlp-en-parser-chunking.bin)
   - When to use: For deep linguistic analysis, understanding the grammatical structure of sentences.

4. **Language Detector Model (langdetect-183.bin)**
   - Purpose: Identifies the language of a given text.
   - Download: [langdetect-183.bin](https://opennlp.apache.org/models/apache-opennlp-models-2.0.0/opennlp-langdetect-183.bin)
   - When to use: When working with multilingual datasets or need to route text to language-specific NLP pipelines.

5. **Lemmatizer Model (en-lemmatizer.bin)**
   - Purpose: Reduces words to their base or dictionary form.
   - Download: [en-lemmatizer.bin](https://opennlp.apache.org/models/apache-opennlp-models-2.0.0/opennlp-en-lemmatizer.bin)
   - When to use: For text normalization, reducing inflectional forms to a common base form.

## Using Additional OpenNLP Models in vouch-nlp

To incorporate these models into vouch-nlp:

1. Download the desired model files and place them in the `models` directory.
2. Update the `src/vouch_nlp/core.clj` file to load and use the new models. For example:

   ```clojure
   (def ner-person (nlp/make-name-finder "models/en-ner-person.bin"))
   (def chunker (nlp/make-treebank-chunker "models/en-chunker.bin"))
   ```

3. Create new functions in `core.clj` or separate namespaces to utilize these models:

   ```clojure
   (defn find-person-names [tokens]
     (ner-person tokens))

   (defn chunk-sentence [sentence]
     (chunker sentence))
   ```

4. Update the main analysis pipeline to incorporate the new capabilities.
5. Add examples and visualizations in the Clerk notebook (`notebooks/nlp_analysis.clj`) to demonstrate the new functionalities.

Remember to handle potential errors if a model file is missing and provide clear instructions in your documentation about which models are required for different functionalities.

## Recommended Workflow

1. Start a REPL in your preferred Clojure development environment.

2. Load the core namespace:
   ```clojure
   (require '[vouch-nlp.core :as core])
   ```

3. Start the Clerk server:
   ```clojure
   (clerk/serve! {:watch-paths ["notebooks" "src"]})
   ```

4. Open the NLP analysis notebook in your browser:
   ```clojure
   (clerk/show! "notebooks/nlp_analysis.clj")
   ```

5. Modify the `sample.txt` file in the `data` directory with the text you want to analyze.

6. Refresh the notebook in your browser to see the updated analysis.

7. Iterate on your analysis by modifying the functions in `src/vouch_nlp/` and updating the notebook as needed.

## Adding New Functionality

1. To add new preprocessing steps:
   - Add functions to `src/vouch_nlp/preprocessing.clj`
   - Update the `preprocess` function to include your new steps

2. To add new analysis techniques:
   - Add functions to `src/vouch_nlp/core.clj`
   - Update the `analyze-text` function to include your new analysis

3. To add new visualizations:
   - Add functions to `src/vouch_nlp/visualization.clj`
   - Use these functions in your Clerk notebook

4. To explore results interactively:
   - Add new sections to `notebooks/nlp_analysis.clj`
   - Use Clerk's data visualization capabilities to create interactive plots and tables

## Best Practices

- Keep functions pure and separate concerns (preprocessing, analysis, visualization).
- Use Clerk for interactive development and visualization of results.
- Commit changes regularly to version control.
- Write tests for your NLP functions to ensure accuracy.
- As the project grows, consider breaking down functionality into more specific namespaces.
- Optimize for performance when dealing with larger datasets.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is open source and available under the [MIT License](LICENSE).
