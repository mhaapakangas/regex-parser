# Testing documentation

## Performance testing

The tests are measuring the time it takes to parse a regular expression and check for a match. The algorithm
performance is compared to the native Java regex. 

Both Thompson's algorithm and the Java native algorithm are based on an NFA to represent the regular expression, but
they have a different strategy for finding if an input matches the regex. Thompson's algorithm traverses the graph once,
holding the set of all reachable states at the same time. The Java native algorithm uses backtracking. It tries to follow
a single path and backtracks if it does not find a match.

Performances were measured for two different regex patterns: `(a|aa)*b` and `(a*a*)*b`.
Each regex pattern is tested against two different types of input, one that matches the regular expressions and one
that matches except for the last character.
The matching input is of the form `(a^n)b`, n being the number of repetitions of `a`. For `n = 3` the input will be `aaab`.
Similarly, the non matching pattern is of the form `(a^n)c`.

## Results

The two different regex pattens were tested against inputs of different lengths ranging from `n = 10` to `n = 1000`.
Each result is the average of 100 executions, except for the result marked with * which is the average of 10 executions.
All the times are in milliseconds.

#### Regex: `(a|aa)*b`

**Input:** `(a^n)b`:

|n| Thompson | Java native|
|---:|---:|---:|
| 10 | 0,003 | 0,009 | 
| 50 | 0,021 | 0,016 | 
| 100 | 0,020 | 0,016 |
| 200 | 0,019 | 0,016 |
| 500 | 0,046 | 0,030 |
| 1000 | 0,106 | 0,050 |

**Input:** `(a^n)c`:

|n| Thompson | Java native|
|---:|---:|---:|
| 10 | 0,003 | 0,014 | 
| 50 | 0,005 | 0,033 | 
| 100 | 0,011 | 0,027 |
| 200 | 0,024 | 0,051 |
| 500 | 0,056 | 0,233 |
| 1000 | 0,071 | 0,251 |


Thompson's algorithm behaves similarly for the two different inputs. The processing time grows linearly with the input
length. In comparison, the Java algorithm performs worse when the input almost matches the regular expression. When the
input is a match, the correct path is found quickly. However, because the input `(a^n)c` matches the input except for the
last character, the algorithm will need to backtrack until all paths have been explored.


#### Regex: `(a*a*)*b`

**Input:** `(a^n)b`:

|n| Thompson | Java native|
|---:|---:|---:|
| 10 | 0,003 | 0,004 | 
| 50 | 0,008 | 0,004 | 
| 100 | 0,010 | 0,003 |
| 200 | 0,012 | 0,006 |
| 500 | 0,047 | 0,011 |
| 1000 | 0,088 | 0,013 |

**Input:** `(a^n)c`:

|n| Thompson | Java native|
|---:|---:|---:|
| 10 | 0,005 | 0,034 |
| 50 | 0,005 | 1,356 |
| 100 | 0,011 | 8,822 | 
| 200 | 0,016 | 74,066 |
| 500 | 0,029 | 1150,010 |
| 1000 | 0,052 | 8881,641* |

Once again Thompson's algorithm performs similarly for the two different inputs. It's processing time also grows
linearly with the input length in this case. The Java algorithm, however, gives significantly worse results with an almost
matching input. The reason is that the Kleene stars from the regex pattern introduce so many paths that the algorithm
has to traverse.


In the best case scenario, the Java native algorithm performs better than Thompson's algorithm. However, there are so-called
pathological regular expressions that perform very slowly for the Java algorithm. Thompson's algorithm does not suffer from
the same problem.

## Performance Measurements

The performances of the Thompson's algorithm implementation and the native Java regex matchers can be measured
using the following command:

```
mvn compile exec:java -Dexec.mainClass=Measurements -Dexec.args="50"
```

The argument represents the size of the generated inputs.

## Sources
- [Russ Cox: Regular Expression Matching Can Be Simple And Fast](https://swtch.com/~rsc/regexp/regexp1.html)
- [Regular Expressions in Java](http://www.amygdalum.net/en/efficient-regular-expressions-java.html)