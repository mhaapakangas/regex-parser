package parser;

import datastructures.Stack;
import models.NFA;
import models.State;

/**
 * Provides functions to build a NFA from a regular expression in postfix notation.
 */
public class NFABuilder {

    /**
     * Transforms a regex in postfix notation into a NFA.
     *
     * @param regex the regex in postfix notation
     * @return the NFA
     */
    public static NFA buildNFA(String regex) {
        if (regex.isEmpty()) {
            return createEpsilonNFA();
        }

        Stack<NFA> stack = new Stack<>();
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

    /**
     * Builds a NFA with an epsilon transition between start and end states.
     *
     * @return the created NFA
     */
    private static NFA createEpsilonNFA() {
        State start = new State(false);
        State end = new State(true);
        start.addEpsilonTransition(end);
        return new NFA(start, end);
    }

    /**
     * Builds a NFA with a symbol transition between start and end states.
     *
     * @param c symbol of the transition
     * @return the created NFA
     */
    private static NFA createSymbolNFA(char c) {
        State start = new State(false);
        State end = new State(true);
        start.setTransition(end, c);
        return new NFA(start, end);
    }

    /**
     * Builds a NFA with two possible paths between start and end states.
     * The different paths are NFAs given as parameters.
     *
     * @param left one NFA
     * @param right the other NFA
     * @return the created NFA
     */
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

    /**
     * Builds a NFA by combining two given NFAs sequentially.
     *
     * @param left the first NFA
     * @param right the second NFA
     * @return the created NFA
     */
    private static NFA createConcatenationNFA(NFA left, NFA right) {
        left.getEnd().addEpsilonTransition(right.getStart());
        left.getEnd().setEnd(false);
        return new NFA(left.getStart(), right.getEnd());
    }

    /**
     * Builds a NFA that represents the Kleene star operation for given expression NFA.
     * Creates a NFA with an epsilon transition between start and end states and between start state and
     * the expression NFA. The expression NFA has two possible transitions: a transition to the end state or
     * a loop to the expression NFA itself.
     *
     * @param nfa the expression NFA
     * @return the created NFA
     */
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
