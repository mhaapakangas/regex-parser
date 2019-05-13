package parser;


import java.util.ArrayDeque;
import java.util.Deque;

public class InputConverter {

    public static String toPostfixNotation(String input) {
        Deque<Character> operatorStack = new ArrayDeque<>();
        String output = "";

        for (char c : input.toCharArray()) {
            switch (c) {
                case '*':
                case '.':
                case '|':
                    char[] lowerPrecedenceChars = getLowerPrecedenceChars(c);
                    while (!operatorStack.isEmpty() && !isOperatorOneOf(lowerPrecedenceChars, operatorStack.peek())) {
                        output += operatorStack.pop();
                    }
                    operatorStack.push(c);
                    break;
                case '(':
                    operatorStack.push(c);
                    break;
                case ')':
                    while (operatorStack.peek() != '(') {
                        output += operatorStack.pop();
                    }
                    operatorStack.pop();
                    break;
                default:
                    output += c;
            }
        }

        while (!operatorStack.isEmpty()) {
            output += operatorStack.pop();
        }

        return output;
    }

    private static char[] getLowerPrecedenceChars(char c) {
        switch (c) {
            case '*':
                return new char[]{'(', '|', '.'};
            case '.':
                return new char[]{'(', '|'};
            default:
                return new char[]{'('};
        }
    }

    private static boolean isOperatorOneOf(char[] chars, char operator) {
        for (char c: chars) {
            if (operator == c) {
                return true;
            }
        }

        return false;
    }

    public static String addConcatenationChar(String input) {
        String output = "";
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            output += ch;
            if (ch == '(' || ch == '|') {
                continue;
            }
            if (i < input.length() - 1) {
                char nextChar = input.charAt(i + 1);
                if (nextChar != ')' && nextChar != '|' && nextChar != '*') {
                    output += '.';
                }
            }
        }
        return output;
    }
}
