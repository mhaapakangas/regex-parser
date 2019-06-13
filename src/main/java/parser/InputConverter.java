package parser;

import datastructures.Stack;

/**
 * Provides functions to convert a regular expression into a postfix notation
 * containing concatenation characters.
 */
public class InputConverter {

    /**
     * Converts the regular expression into a postfix notation.
     *
     * The conversion uses the following rules for operator precedence:
     * Kleene star has the highest precedence, followed by the concatenation operator.
     * The pipe has the lowest precedence.
     *
     * @param input the regex with concatenation characters
     * @return the regex in postfix notation
     * @throws NullPointerException if the regex is malformed
     */
    public static String toPostfixNotation(String input) throws NullPointerException {
        Stack<Character> operatorStack = new Stack<>();
        char[] output = new char[input.length()];
        int outputIndex = 0;

        for (char c : input.toCharArray()) {
            switch (c) {
                case '*':
                case '.':
                case '|':
                    char[] lowerPrecedenceChars = getLowerPrecedenceChars(c);
                    while (!operatorStack.isEmpty() && !isOperatorOneOf(lowerPrecedenceChars, operatorStack.peek())) {
                        output[outputIndex++] = operatorStack.pop();
                    }
                    operatorStack.push(c);
                    break;
                case '(':
                    operatorStack.push(c);
                    break;
                case ')':
                    while (operatorStack.peek() != '(') {
                        output[outputIndex++] = operatorStack.pop();
                    }
                    operatorStack.pop();
                    break;
                default:
                    output[outputIndex++] = c;
            }
        }

        while (!operatorStack.isEmpty()) {
            output[outputIndex++] = operatorStack.pop();
        }

        return new String(output, 0, outputIndex);
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
        for (char c : chars) {
            if (operator == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds concatenation characters to the regular expression.
     *
     * Concatenation character '.' marks that two expressions should be concatenated.
     * It is added after each character of the input that is not an opening parenthesis
     * or a pipe, and that is not followed by a closing parenthesis, a pipe or a Kleene star.
     *
     * @param input the regex
     * @return the regex with concatenation characters
     */
    public static String addConcatenationChar(String input) {
        if (input.isEmpty()) {
            return "";
        }
        char[] output = new char[2 * input.length()];
        int outputIndex = 0;

        for (int i = 0; i < input.length() - 1; i++) {
            char c = input.charAt(i);
            output[outputIndex++] = c;
            if (c == '(' || c == '|') {
                continue;
            }

            char nextChar = input.charAt(i + 1);
            if (nextChar != ')' && nextChar != '|' && nextChar != '*') {
                output[outputIndex++] = '.';
            }
        }
        output[outputIndex++] = input.charAt(input.length() - 1);

        return new String(output, 0, outputIndex);
    }
}
