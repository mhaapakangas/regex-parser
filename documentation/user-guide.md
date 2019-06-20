# User guide

The program can be used to check whether a text matches exactly a regular expression or not.

To run the program, download the jar from [github](https://github.com/mhaapakangas/regex-parser/releases/tag/1.0) and execute it with `java -jar thompson.jar`. The program first asks for
the regular expression and then for the text to test. It then returns as a result if the regex and the test are an exact
match or not.

The regex parser supports the following operators:

| Operator | Description |
|:---:|:---|
| * | Match an expression 0 or more times |
| &#124; | Match either expression |
| () | Group expressions |

The listed operators cannot be used as regular characters in the regex. Additionally, the program is using `.` as a
reserved character, so it cannot be used either in the regular expression.