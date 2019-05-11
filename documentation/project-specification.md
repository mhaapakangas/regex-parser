# Project specification
The aim of the project is to implement a parser for regular expressions in Java. The regex parser will be
based on Thompson's algorithm. It will support the most common operators: '*' for matching a pattern zero
or more times, '|' for alternative patterns, and '(..)' for grouping expressions.

## Data structures and algorithms
Thompson's algorithm transforms a regular expression into a nondeterministic finite automaton (NFA).
The regex input string is first converted into an equivalent postfix notation.

The state machine is stored as a directed graph. For each character in the postfix notation,
the algorithm adds equivalent nodes (states) and edges (transitions) to the graph. The graph is constructed
using two stacks.

The starting state and finish state are marked in the NFA graph. If there is a possible path from starting node
to the end node for a provided text input, then it matches the regular expression.

## Usage
User gives two inputs: the regular expression and the text to match against. The output
of the program will be a list of matches in the given text.

## Sources

- [Wikipedia: Thompson's construction](https://en.wikipedia.org/wiki/Thompson%27s_construction)
- [Matt Might: Parsing regular expressions with recursive descent](http://matt.might.net/articles/parsing-regex-with-recursive-descent/)
- [Russ Cox: Regular Expression Matching Can Be Simple And Fast](https://swtch.com/~rsc/regexp/regexp1.html)
- [Amer Gerzic: Write Your Own Regular Expression Parser](https://www.codeguru.com/cpp/cpp/cpp_mfc/parsing/article.php/c4093/Write-Your-Own-Regular-Expression-Parser.htm)
- [Denis Kyashif: Implementing a Regular Expression Engine](https://deniskyashif.com/implementing-a-regular-expression-engine/)  