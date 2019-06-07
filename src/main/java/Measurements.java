import matcher.RegexMatcher;
import models.NFA;
import parser.InputConverter;
import parser.NFABuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Measurements {
    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide regex size");
            System.exit(1);
        }

        int limit = Integer.valueOf(args[0]);

        // warm up the JVM
        for (int i = 0; i < 10000; i++) {
            runRegexParser("aaaaa", "aaaaa", true);
            runJavaParser("aaaaa", "aaaaa", true);
        }

        String regexp = "";
        String input = "";
        for (int i = 0; i < limit; i++) {
            regexp = "a*" + regexp + "a";
            input += "a";
        }

        System.out.println("Regex: (a*)^n a^n, Input: a^n, n = " + limit);
        runRegexParser(regexp, input, false);
        runJavaParser(regexp, input, false);

        regexp = "";
        input = "";
        for (int i = 0; i < limit; i++) {
            regexp += "a";
            input += "a";
        }

        System.out.println("\nRegex: a^n, Input: a^n, n = " + limit);
        runRegexParser(regexp, input, false);
        runJavaParser(regexp, input, false);

        EXECUTOR.shutdown();
        System.exit(0);
    }

    private static void runRegexParser(String regex, String input, Boolean quiet) {
        final Future<Void> handler = EXECUTOR.submit(new Callable<Void>() {
            @Override
            public Void call() {
                Instant start = Instant.now();
                String prepared = InputConverter.addConcatenationChar(regex);
                NFA nfa = NFABuilder.buildNFA(InputConverter.toPostfixNotation(prepared));
                Instant time1 = Instant.now();
                boolean result = RegexMatcher.match(nfa, input);
                Instant finish = Instant.now();
                long timeElapsed = Duration.between(start, finish).toMillis();
                if (!quiet) {
                    System.out.println("NFA building: " + Duration.between(start, time1).toMillis() + "ms");
                    System.out.println("Matching: " + Duration.between(time1, finish).toMillis() + "ms");
                    System.out.println("Regex-parser - is a match: " + result + ", time: " + timeElapsed + "ms");
                }
                return null;
            }
        });

        try {
            handler.get(TIMEOUT.toMillis(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            handler.cancel(true);
            System.out.println("Regex-parser - took over 10s");
        }
    }

    private static void runJavaParser(String regex, String input, Boolean quiet) {
        final Future<Void> handlerJava = EXECUTOR.submit(new Callable<Void>() {
            @Override
            public Void call() {
                Instant start = Instant.now();
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(input);
                boolean result = matcher.find();
                Instant finish = Instant.now();
                long timeJava = Duration.between(start, finish).toMillis();
                if (!quiet) {
                    System.out.println("Java regex - is a match: " + result + ", time: " + timeJava + "ms");
                }
                return null;
            }
        });

        try {
            handlerJava.get(TIMEOUT.toMillis(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            handlerJava.cancel(true);
            System.out.println("Java regex - took over 10s");
        }
    }
}

