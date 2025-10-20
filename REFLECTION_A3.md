 # Reflection for Assignment 3

In Assignment 2, I implemented the ETL pipeline in a more procedural style. The program was functional, but much of the logic was concentrated into a single flow, which made the design less clear and harder to adapt. The code that read the CSV file, applied transformations, and wrote output was all tied together in one place. While this worked for the assignment, it also showed me the limits of a procedural approach: if I wanted to swap out just one step, like using a different file format or adding new transformations, I would have needed to rewrite part of the main program.

In Assignment 3, I restructured the solution using object-oriented design. Instead of one large program, I created multiple classes with distinct responsibilities:
- The `CsvExtractor` handles reading the input file.
- The `ProductTransformations` class handles transformations and validation.
- The `CsvLoader` handles writing results to the output file.
- The `ETLPipeline` class coordinates the full extract-transform-load sequence and provides a builder pattern for assembling these parts.

This separation of concerns made the project much easier to understand and extend. For example, if I wanted to later add a JSON extractor or a database loader, I could do so without rewriting the rest of the pipeline. Each class focuses on a single responsibility, which makes the overall codebase cleaner and more maintainable. This also demonstrated encapsulation (each class hides its implementation details) and polymorphism (I could substitute one extractor, transformer, or loader with another, as long as it followed the same interface).

Compared to Assignment 2, the new design feels far more reusable. The procedural version solved the immediate problem, but the object-oriented version creates a framework I could build on for many different use cases. It was also easier to document and reason about, since each part of the pipeline could be explained on its own.

I did use a generative AI assistant in this assignment, but mainly as a helper for brainstorming and confirming design choices. For example, I asked how I might break down the ETL process into separate classes, and the AI suggested an extractor/transformer/loader split, which matched the direction I was already considering. It also suggested using a builder pattern for assembling the pipeline, which I found useful and decided to implement. However, I wrote the actual code  and adjusted the design so that it stayed consistent with my work from Assignment 2. For example, I kept the `price` field in the `Product` model as a string instead of converting it to a number, because that matched my earlier implementation.

Reflecting on this assignment, I learned how valuable object-oriented decomposition can be. By creating small, focused classes and connecting them through interfaces, I made the program much easier to test, extend, and maintain. I also learned how to balance using AI as a supportive tool: it can give useful ideas or reminders, but it is up to me to adapt those suggestions and make the final design decisions. 

Overall, the redesign gave me confidence that I can take a working but procedural program and improve it with object-oriented principles. I now better understand how encapsulation, modularity, and design patterns like the builder can make code more professional and extensible. This assignment showed me the practical difference between “code that works” and “code that is designed to grow.”