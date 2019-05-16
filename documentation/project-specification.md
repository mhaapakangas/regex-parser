# Project specification
The aim of the project is to implement a parser for regular expressions in Java. The regex parser will be
based on Thompson's algorithm. It will support the most common operators: '*' for matching a pattern zero
or more times, '|' for alternative patterns, and '(..)' for grouping expressions.

## Data structures and algorithms
Thompson's algorithm transforms a regular expression into a nondeterministic finite automaton (NFA).
The regex input string is first converted into an equivalent postfix notation. This notation also
contains an added character to represent concatenation of two other expressions. E.g. `ab` will be transformed
into `ab.`.

The state machine is stored as a directed graph. For each character in the postfix notation,
the algorithm adds equivalent nodes (states) and edges (transitions) to the graph. The graph is constructed
using two stacks.

The starting state and finish state are marked in the NFA graph. If there is a possible path from starting node
to the end node for a provided text input, then it matches the regular expression.

## Usage
User gives two inputs: the regular expression and the text to match against. The output
of the program will be a list of matches in the given text.

## Complexity

Converting the regex to the postfix notation is done in 2 steps: adding concatenation characters to
the regular expression and converting it into postfix notation. Each of these steps is done with an _O(n)_ time complexity,
_n_ being the length of the regular expression.

The expected space complexity of Thompson's algorithm is _O(n)_, where _n_ is the size of the 
regular expression. The constructed graph will have _2s - c_ nodes, where _s_ is the number of symbols in
the regex apart from parentheses and _c_ is the number of concatenation symbols in the regex.

The expected time complexity is _O(nm)_, where _n_ is the size of the regular expression and _m_ is the size
of the text to match against. The time complexity for an NFA is _O(enm)_ where _n_ is the number of states
in the graph, _e_ is the maximum number of transitions for each state, and _m_ is the size of the input.
Thompson's NFA is created with at most 2 transitions per state, and a linear number of states.

## Sources

- [Wikipedia: Thompson's construction](https://en.wikipedia.org/wiki/Thompson%27s_construction)
- [Matt Might: Parsing regular expressions with recursive descent](http://matt.might.net/articles/parsing-regex-with-recursive-descent/)
- [Russ Cox: Regular Expression Matching Can Be Simple And Fast](https://swtch.com/~rsc/regexp/regexp1.html)
- [Amer Gerzic: Write Your Own Regular Expression Parser](https://www.codeguru.com/cpp/cpp/cpp_mfc/parsing/article.php/c4093/Write-Your-Own-Regular-Expression-Parser.htm)
- [Denis Kyashif: Implementing a Regular Expression Engine](https://deniskyashif.com/implementing-a-regular-expression-engine/)  