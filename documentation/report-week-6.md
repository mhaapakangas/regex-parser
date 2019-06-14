# Progress report: Week 6

This week I improved the string operations used in the algorithm my replacing string concatenation
with an array. I've also added analysis of the complexity of the rest of the algorithm to the implementation
documentation.

For performance testing I replaced the previous test inputs with simpler examples. I selected two different regex patterns
that result in different behavior in Thompson's algorithm and Java native regex parser when inputs that match and almost match
the regex are compared.

I also tried to take measurements with grep, but the time it took to start the process was much longer than the running time
of grep, making the results unusable. 

For the final week, I'm planning to add more explanation about the structure of the NFA to the implementation documentation, as
well as some graphs. I'll try to investigate how to get proper measurements with grep, and improve the testing documentation
with graphs and more in-depth analysis.

**Time spent:** 12 h