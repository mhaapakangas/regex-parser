package matcher;

import models.NFA;
import models.State;

import java.util.HashSet;
import java.util.Set;

/**
 * Provides functions to check if a text matches a regular expression.
 */
public class RegexMatcher {

    /**
     * Checks if the given input matches the regular expression.
     *
     * @param nfa NFA representing the regex
     * @param input text to match
     * @return true if the input is an exact match, otherwise false
     */
    public static boolean match(NFA nfa, String input) {
        Set<State> states = new HashSet<>();
        findNextStates(nfa.getStart(), states);

        for (char c : input.toCharArray()) {
            Set<State> nextStates = new HashSet<>();

            for (State state : states) {
                if (state.getSymbol() != null && state.getSymbol() == c) {
                    findNextStates(state.getTransition(), nextStates);
                }
            }
            states = nextStates;
        }

        return states.stream().anyMatch(State::isEnd);
    }

    private static void findNextStates(State state, Set<State> nextStates) {
        if (!state.hasEpsilonTransition()) {
            nextStates.add(state);
        } else {
            for (int i = 0; i < 2; i++) {
                State next = state.getEpsilonTransitions()[i];
                if (next != null) {
                    findNextStates(next, nextStates);
                }
            }
        }
    }
}
