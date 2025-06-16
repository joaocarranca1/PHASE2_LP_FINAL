# X++ Language Interpreter

A parser and interpreter for the X++ functional programming language with typechecking and recursion.

## Features

- **Parser**: JavaCC-based syntax analysis
- **Interpreter**: Evaluates and typechecks expressions with:
  -unions
  -structs
  -Lists
  -Recursion 
  -More basic expressions 
Program also makes use of subtyping.
  - 
## Quick Start

1. **Build**:
   ```bash
   rm *.class
   javacc ParserL0.jj
   javac *.java
   chmod +x x++

2. ## RUN

java L0int
