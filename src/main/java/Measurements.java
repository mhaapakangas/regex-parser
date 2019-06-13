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
            javaParser("(aaaaa*)|a", "aaaaa", true, 1);
            regexParser("(aaaaa*)|a", "aaaaa", true, 1);
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
        regexParser(regexp, input, true, 1);
        javaParser(regexp, input, true, 1);
        System.out.println(text);
        regexParser(regexp, input, false, 10);
        javaParser(regexp, input, false, 10);
    }

    private static void regexParser(String regex, String input, Boolean quiet, int n) {
        long totalTime = 0;
        for (int i = 0; i < n; i++) {
            Instant start = Instant.now();
            String prepared = InputConverter.addConcatenationChar(regex);
            NFA nfa = NFABuilder.buildNFA(InputConverter.toPostfixNotation(prepared));
            RegexMatcher.match(nfa, input);
            Instant finish = Instant.now();
            totalTime += Duration.between(start, finish).toNanos();
        }

        if (!quiet) {
            System.out.println("Regex-parser - time: " + String.format("%.3f", totalTime / 1000000.0 / n) + "ms");
        }
    }

    private static void javaParser(String regex, String input, Boolean quiet, int n) {
        long totalTime = 0;
        for (int i = 0; i < n; i++) {
            Instant start = Instant.now();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            matcher.find();
            Instant finish = Instant.now();
            totalTime += Duration.between(start, finish).toNanos();
        }

        if (!quiet) {
            System.out.println("Native Java parser - time: " +  String.format("%.3f", totalTime / 1000000.0 / n)   + "ms");
        }
    }
}

