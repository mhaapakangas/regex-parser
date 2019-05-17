# Progress report: Week 2

This week I implemented the input conversion to convert the regex into a postfix notation.
It's done in two steps: first adding concatenation characters and then transforming the
string into a postfix notation. The current implementation uses Java ArrayDeque, which will be
replaced with a stack implementation later.

In addition, I started working on building the NFA graph. The code and unit tests for this
logic are not yet final. Next week's plan is to finish the NFA building and start using it for
regex matching. As an improvement, I'm planning to change test names in InputConverterTest to
more informative ones. 

**Time spent:** 7 h