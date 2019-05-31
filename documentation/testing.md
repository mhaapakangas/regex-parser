# Testing documentation

## Performance testing

The tests are measuring the time it takes to parse a regular expression and check for a match. The algorithm
performance is compared to the native Java regex and to grep. 
 
## Performance Measurements

The performances of the implementation and of the native Java regex matchers can be measured using the following command:

```
mvn compile exec:java -Dexec.mainClass=Measurements -Dexec.args="50"
```

The class takes as an argument the length of the regular expressions that should be given to the regex matchers. Regular 
expressions with different properties are built and tested sequentially with both implementations.

Measurements for the same regular expressions using grep can be made with the following command:

```
./performance-testing/measurement.sh 50
```

The argument also represents the length of the generated regular expressions.

## Sources
- [Russ Cox: Regular Expression Matching Can Be Simple And Fast](https://swtch.com/~rsc/regexp/regexp1.html)