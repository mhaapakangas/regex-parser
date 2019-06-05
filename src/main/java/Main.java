import matcher.RegexMatcher;
import models.NFA;
import parser.InputConverter;
import parser.NFABuilder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter regex: ");
        String regex = scanner.nextLine();
        String prepared = InputConverter.addConcatenationChar(regex);
        NFA nfa = NFABuilder.buildNFA(InputConverter.toPostfixNotation(prepared));

        System.out.println("\nEnter a string to match: ");
        String input = scanner.nextLine();

        boolean result = RegexMatcher.match(nfa, input);
        System.out.println("\nis a match: " + result);

    }
}
