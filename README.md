# Regular expression parser

Project for Data structures and algorithms -course.

## Documentation

### [Project specification](documentation/project-specification.md)
### [Implementation document](documentation/implementation.md)
### [Testing document](documentation/testing.md)

### Progress reports

[Week 1](documentation/report-week-1.md)

[Week 2](documentation/report-week-2.md)

[Week 3](documentation/report-week-3.md)

[Week 4](documentation/report-week-4.md)

[Week 5](documentation/report-week-5.md)

[Week 6](documentation/report-week-6.md)

### Instructions

Compile and run the application:
```
mvn compile exec:java -Dexec.mainClass=Main
```

Generate a jar: 
```
mvn package
```

Run the generated jar:
```
java -jar target/regex-parser-1.0-SNAPSHOT.jar
```

Generate test coverage report (can be found under _target/site/jacoco/index.html_):
```
mvn test jacoco:report
```

Generate javadoc (can be found under _target/site/apidocs/index.html_):
```
mvn javadoc:javadoc
```
