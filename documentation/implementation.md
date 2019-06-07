# Project implementation

## Project structure

![package diagram](resources/package_diagram.jpg)

The algorithm has two parts: the parsing of a regular expression into an NFA and the matching of an input text
 to the regex. The classes involved in the parsing are *InputConverter* and *NFABuilder*. First, the *InputConverter*
 adds concatenation characters to the regex given by the user and converts it into postfix notation. The *NFABuilder*
 then constructs the NFA based on the regex in postfix notation. The *RegexMatcher* is in charge of checking whether an
 input text matches the given regular expression. 
 
 Both *NFABuilder* and *RegexMatcher* are using the *NFA* and *State* models to represent the state machine. The
 algorithms for *InputConverter*, *NFABuilder*, and *RegexMatcher* are based on a stack and they use the implementation from the 
 *datastructures* package.
 
 ### Regex parsing
 
 The first step of the algorithm is to add concatenation characters to the regular expression. The algorithm loops over
 each character of the given regex, and adds concatenation characters (`.`) to mark which expressions should be concatenated.
 The character is added after each character of the input that is not an opening parenthesis or a pipe, and that is not
 followed by a closing parenthesis, a pipe or a Kleene star.
 
 Because the algorithm consists of a simple for loop, its time complexity if O(n) for a regex of size n. In the worst case, the
 algorithm would add a concatenation character for each character in the given regex, it also has the space complexity of O(n).