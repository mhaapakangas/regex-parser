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
        int stackId = 1;
        Stack<State> states = new Stack<>();
        addNextStatesToStack(nfa.getStart(), states, stackId);

        for (char c : input.toCharArray()) {
            Stack<State> nextStates = new Stack<>();
            stackId++;

            while (states.peek() != null) {
                State state = states.pop();
                if (state.getSymbol() != null && state.getSymbol() == c) {
                    addNextStatesToStack(state.getTransition(), nextStates, stackId);
                }
            }
            states = nextStates;
        }

        while (states.peek() != null) {
            State state = states.pop();
            if (state.isEnd()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Find next states from the given one following the epsilon transitions as far as possible, and add them to the stack.
     * If a state has already been added to the current stack (stackId equals lastStackId) then it is ignored.
     *
     * @param state starting state
     * @param stack stack where the states should be added
     * @param stackId ID of the current stack
     */
    private static void addNextStatesToStack(State state, Stack<State> stack, int stackId) {
        if (!state.hasEpsilonTransition()) {
            if (stackId == state.getLastStackId()) {
                return;
            }
            state.setLastStackId(stackId);
            stack.push(state);
        } else {
            for (int i = 0; i < 2; i++) {
                State next = state.getEpsilonTransitions()[i];
                if (next != null) {
                    addNextStatesToStack(next, stack, stackId);
                }
            }
        }
    }
}
