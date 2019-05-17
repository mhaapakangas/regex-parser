import parser.InputConverter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter regex: ");
        String input = scanner.nextLine();
        String prepared = InputConverter.addConcatenationChar(input);
        System.out.println("Postfix notation: " + InputConverter.toPostfixNotation(prepared));
    }
}
