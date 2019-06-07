# Progress report: Week 5

[First code review](https://github.com/eevalaiho/FocusStacking/issues/1)

The algorithm in RegexMatcher for matching an input to the regex was modified to use a helper field to keep track of the states
that have been visited or added for each iteration. Because of this I could replace the java.util.HashSet with the own
stack implementation. In addition, I fixed the bug for regex containing recursive Kleene stars, and added logic to print
an error message if the user gives an invalid regex.

I continued working on performance testing. The preliminary results show that the algorithm performs much better than
the native Java one for situations where the Java algorithm has to do backtracking. The algorithm seemed to be slightly
slower than grep but to have a similar complexity.

Next week, I'm planning to continue testing based on this week's results and to analyze the complexity of other parts 
of the algorithm for the implementation documentation.

**Time spent:** 12 h