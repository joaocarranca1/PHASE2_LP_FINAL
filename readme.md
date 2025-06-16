# L1++ Language Interpreter

A parser and interpreter for the L1 functional programming language with strict/lazy list support.

## Features

- **Parser**: JavaCC-based syntax analysis
- **Interpreter**: Evaluates expressions with:
  - Boxes (mutable references)
  - Strict lists (`::`) and lazy lists (`:?`)
  - Pattern matching (`match`)
  - First-class functions

## Quick Start

1. **Build**:
   ```bash
   rm *.class
   javacc ParserL0.jj
   javac *.java

2. ## RUN

java L0int