import matcher.RegexMatcher;
import parser.InputConverter;
import parser.NFABuilder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter regex: ");
        String regex = scanner.nextLine();
        String prepared = InputConverter.addConcatenationChar(regex);
        String postfix = InputConverter.toPostfixNotation(prepared);

        System.out.println("Enter string to match: ");
        String input = scanner.nextLine();
        boolean result = RegexMatcher.match(NFABuilder.buildNFA(postfix), input);
        System.out.println("result: " + result);
    }
}
