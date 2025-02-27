Formal Languages and Translators - Java Project

Overview

This project was developed as part of the Laboratorio di Linguaggi Formali e Traduttori course at the University of Turin (A.A. 2022/2023). The primary objective was to implement key concepts related to formal languages, lexical analysis, syntax parsing, and translation techniques using Java. The project includes:

Deterministic Finite Automata (DFA) implementations

Lexical Analyzer (Lexer) for tokenizing a simple programming language

Recursive Descent Parser (Syntax Analyzer) for parsing expressions

Syntax-Directed Translator for evaluating expressions

Bytecode Generator to produce JVM-compatible bytecode using Jasmin assembler

Features

1. Deterministic Finite Automata (DFA)

Implemented DFAs for recognizing specific patterns in binary strings.

Designed a DFA to recognize Java-style identifiers.

Developed a DFA to classify student identifiers based on predefined rules.

Implemented a DFA for recognizing floating-point numbers in scientific notation.

2. Lexical Analyzer (Lexer)

Reads input from a file and converts it into a sequence of tokens.

Supports numbers, identifiers, keywords, arithmetic operators, and delimiters.

Ignores comments (both /* */ and // styles).

Detects invalid characters and syntax errors.

3. Syntax Analyzer (Recursive Descent Parser)

Parses arithmetic expressions using operator precedence rules.

Recognizes a custom programming language using a top-down parsing approach.

Reports syntax errors with detailed messages.

4. Syntax-Directed Translator (Expression Evaluator)

Implements evaluation rules while parsing arithmetic expressions.

Uses attribute grammar techniques to compute results dynamically.

Supports operator precedence for +, -, *, / operations.

5. Bytecode Generator for the JVM

Translates the custom programming language into JVM bytecode.

Uses Jasmin assembler to convert the generated bytecode into an executable .class file.

Supports:

Variable assignments (assign 3 to x)

Input (read[x])

Output (print[x])

Loops (while (condition) { ... })

Conditionals (conditional [ option(condition) do { ... } ] end)

Technologies Used

Java (Core implementation)

Jasmin Assembler (Bytecode generation for the JVM)

Regex & Finite Automata (Lexical analysis and tokenization)

Recursive Descent Parsing (Syntax analysis)

Syntax-Directed Translation (Expression evaluation)

How to Run

Compile the Java files:

javac *.java

Run the Lexer (for lexical analysis):

java Lexer input.txt

Run the Parser (for syntax analysis):

java Parser input.txt

Run the Translator (for generating JVM bytecode):

java Translator input.lft

Convert to JVM bytecode using Jasmin:

java -jar jasmin.jar Output.j

Run the compiled Java bytecode:

java Output

Why This Project Matters

This project provides a hands-on approach to understanding lexical analysis, parsing, syntax-directed translation, and bytecode generation. It simulates real-world compiler design principles and can serve as a starting point for learning language processing and compiler construction.

Author

Hussein - Developed this project during the Laboratorio di Linguaggi Formali e Traduttori course at the University of Turin.
