import matcher.RegexMatcher;
import models.NFA;
import parser.InputConverter;
import parser.NFABuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Measurements {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide input size");
            System.exit(1);
        }

        int limit = Integer.valueOf(args[0]);

        String inputBase = "";
        for (int i = 0; i < limit; i++) {
            inputBase += "a";
        }

        // warm up the JVM
        for (int i = 0; i < 100000; i++) {
            javaParser("(aaaaa*)|a", "aaaaa", true);
            regexParser("(aaaaa*)|a", "aaaaa", true);
        }

        String regexp = "(a|aa)*b";
        String input = inputBase + "b";
        runMeasurement(regexp, input, "\nRegex: (a|aa)*b, Input: (a^n)b, n = " + limit);
        input = inputBase + "c";
        runMeasurement(regexp, input, "\nRegex: (a|aa)*b, Input: (a^n)c, n = " + limit);

        regexp = "(a*a*)*b";
        input = inputBase + "b";
        runMeasurement(regexp, input, "\nRegex: (a*a*)*b, Input: (a^n)b, n = " + limit);
        input = inputBase + "c";
        runMeasurement(regexp, input, "\nRegex: (a*a*)*b, Input: (a^n)c, n = " + limit);

        System.exit(0);
    }

    private static void runMeasurement(String regexp, String input, String text) {
        regexParser(regexp, input, true);
        javaParser(regexp, input, true);
        System.out.println(text);
        regexParser(regexp, input, false);
        javaParser(regexp, input, false);
    }

    private static void regexParser(String regex, String input, Boolean quiet) {
        Instant start = Instant.now();
        String prepared = InputConverter.addConcatenationChar(regex);
        NFA nfa = NFABuilder.buildNFA(InputConverter.toPostfixNotation(prepared));
        boolean result = RegexMatcher.match(nfa, input);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toNanos();
        if (!quiet) {
            System.out.println("Regex-parser - is a match: " + result + ", time: " + timeElapsed / 1000000.0 + "ms");
        }
    }

    private static void javaParser(String regex, String input, Boolean quiet) {
        Instant start = Instant.now();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        boolean result = matcher.find();
        Instant finish = Instant.now();
        long timeJava = Duration.between(start, finish).toNanos();
        if (!quiet) {
            System.out.println("Java regex - is a match: " + result + ", time: " + timeJava / 1000000.0 + "ms");
        }
    }
}

