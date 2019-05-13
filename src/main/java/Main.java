import parser.InputConverter;

public class Main {
    public static void main(String[] args) {
        String prepared = InputConverter.addConcatenationChar("(a|b)*c");
        System.out.println(InputConverter.toPostfixNotation(prepared));
    }
}
