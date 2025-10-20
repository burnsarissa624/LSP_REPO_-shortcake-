 # AI Prompts Log – Assignment 3

This document records some of the AI prompts I used while working on Assignment 3 and short excerpts of responses. It demonstrates how I used AI as a helper in redesigning my ETL pipeline with an object-oriented approach.

---

## Prompt 1
**My prompt:**  
“How can I redesign my ETL pipeline to be more object-oriented in Java?”

**AI response (excerpt):**  
“Consider splitting the pipeline into separate classes: `Extractor`, `Transformer`, and `Loader`. Then use a `Pipeline` class to coordinate them. This will improve encapsulation and separation of concerns.”

---

## Prompt 2
**My prompt:**  
“Can you give me an example of a Product class with methods to parse CSV rows?”  

**AI response (excerpt):**  
“Here is a simple `Product` model with fields for id, name, category, and price. It includes a `fromCsv` static factory method and a `toCsvRow` method for output.”

---

## Prompt 3
**My prompt:**  
“I need to generate Javadocs for my classes. What should I add?”  

**AI response (excerpt):**  
“You should include a class-level description, and for each public method add `@param`, `@return`, and `@throws` where applicable. For example:  
```java
/**
 * Runs the pipeline end-to-end.
 * @return number of records written
 * @throws Exception if extraction, transformation, or loading fails
 */
```”

---

## Prompt 4
**My prompt:**  
“How do I compile and run my Main class in the correct package?”  

**AI response (excerpt):**  
“Run these commands from your project root:  
```bash
rm -rf out
mkdir out
javac -d out $(find src -name "*.java")
java -cp out org.howard.edu.lsp.assignment3.Main
```”

---

# Summary
I mainly used the AI assistant to brainstorm the OO design, generate example boilerplate for model and pipeline classes, and clarify the correct way to build/run the project.
