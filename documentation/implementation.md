# Project implementation

## Project structure

![package diagram](resources/package_diagram.jpg)

The algorithm has two parts: the parsing of a regular expression into an NFA and the matching of an input text
 to the regex. The classes involved in the parsing are *InputConverter* and *NFABuilder*. First, the *InputConverter*
 adds concatenation characters to the regex given by the user and converts it into postfix notation. The *NFABuilder*
 then constructs the NFA based on the regex in postfix notation. The *RegexMatcher* is in charge of checking whether an
 input text matches the given regular expression. 
 
 Both *NFABuilder* and *RegexMatcher* are using the *NFA* and *State* models to represent the state machine. The
 algorithms for *InputConverter* and *NFABuilder* are based on a stack and they use the implementation from the 
 *datastructures* package.