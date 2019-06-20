package matcher;

import datastructures.Stack;
import models.NFA;
import models.State;

/**
 * Provides functions to check if a text matches a regular expression.
 */
public class RegexMatcher {

    /**
     * Checks if the given input matches the regular expression.
     * The algorithm keeps track of all possible paths in the NFA simultaneously
     * so that there is no backtracking.
     *
     * @param nfa NFA representing the regex
     * @param input text to match
     * @return true if the input is an exact match, otherwise false
     */
    public static boolean match(NFA nfa, String input) {
        int iterationId = 1;
        Stack<State> states = new Stack<>();
        addNextStatesToStack(nfa.getStart(), states, iterationId);

        for (char c : input.toCharArray()) {
            Stack<State> nextStates = new Stack<>();
            iterationId++;

            while (states.peek() != null) {
                State state = states.pop();
                if (state.getSymbol() != null && state.getSymbol() == c) {
                    addNextStatesToStack(state.getTransition(), nextStates, iterationId);
                }
            }
            states = nextStates;
        }

        return containsEndState(states);
    }

    private static boolean containsEndState(Stack<State> states) {
        while (states.peek() != null) {
            State state = states.pop();
            if (state.isEnd()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find next states from the given one following the epsilon transitions as far as possible,
     * and add reached states to the stack. The method marks all visited states with the current iteration ID to avoid following
     * epsilon transitions that form a cycle in the NFA. Because of the way the NFA is built, this also prevents
     * multiple additions of the same state to the stack.
     *
     * @param state starting state
     * @param stack stack where the states should be added
     * @param iterationId ID of the current iteration
     */
    private static void addNextStatesToStack(State state, Stack<State> stack, int iterationId) {
        state.setLastVisitedId(iterationId);

        if (!state.hasEpsilonTransition()) {
            stack.push(state);
        } else {
            for (int i = 0; i < 2; i++) {
                State next = state.getEpsilonTransitions()[i];
                if (next != null && next.getLastVisitedId() != iterationId) {
                    addNextStatesToStack(next, stack, iterationId);
                }
            }
        }
    }
}
