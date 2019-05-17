package parser;

import java.util.ArrayDeque;
import java.util.Deque;

public class NFABuilder {

    public static NFA buildNFA(String regex) {
        if (regex.isEmpty()) {
            return createEpsilonNFA();
        }

        Deque<NFA> stack = new ArrayDeque<>();
        for (char c : regex.toCharArray()) {
            switch (c) {
                case '*':
                    stack.push(createKleeneStarNFA(stack.pop()));
                    break;
                case '.':
                    NFA concatRight = stack.pop();
                    NFA concatLeft = stack.pop();
                    stack.push(createConcatenationNFA(concatLeft, concatRight));
                    break;
                case '|':
                    NFA unionRight = stack.pop();
                    NFA unionLeft = stack.pop();
                    stack.push(createUnionNFA(unionLeft, unionRight));
                    break;
                default:
                    stack.push(createSymbolNFA(c));
            }
        }

        return stack.pop();
    }

    private static NFA createEpsilonNFA() {
        State start = new State(false);
        State end = new State(true);
        start.addEpsilonTransition(end);
        return new NFA(start, end);
    }

    private static NFA createSymbolNFA(char c) {
        State start = new State(false);
        State end = new State(true);
        start.setTransition(end, c);
        return new NFA(start, end);
    }

    private static NFA createUnionNFA(NFA left, NFA right) {
        State start = new State(false);
        State end = new State(true);
        start.addEpsilonTransition(left.getStart());
        start.addEpsilonTransition(right.getStart());

        left.getEnd().addEpsilonTransition(end);
        left.getEnd().setEnd(false);
        right.getEnd().addEpsilonTransition(end);
        right.getEnd().setEnd(false);

        return new NFA(start, end);
    }

    private static NFA createConcatenationNFA(NFA left, NFA right) {
        left.getEnd().addEpsilonTransition(right.getStart());
        left.getEnd().setEnd(false);
        return new NFA(left.getStart(), right.getEnd());
    }

    private static NFA createKleeneStarNFA(NFA nfa) {
        State start = new State(false);
        State end = new State(true);
        start.addEpsilonTransition(end);

        start.addEpsilonTransition(nfa.getStart());
        nfa.getEnd().addEpsilonTransition(nfa.getStart());
        nfa.getEnd().addEpsilonTransition(end);
        nfa.getEnd().setEnd(false);

        return new NFA(start, end);
    }
}
