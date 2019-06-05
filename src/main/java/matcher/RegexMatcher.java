package matcher;

import models.NFA;
import models.State;

import java.util.ArrayList;
import java.util.List;

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
        int listId = 1;
        List<State> states = new ArrayList<>();
        findNextStates(nfa.getStart(), states, listId);

        for (char c : input.toCharArray()) {
            List<State> nextStates = new ArrayList<>();
            listId++;

            for (State state : states) {
                if (state.getSymbol() != null && state.getSymbol() == c) {
                    findNextStates(state.getTransition(), nextStates, listId);
                }
            }
            states = nextStates;
        }

        return states.stream().anyMatch(State::isEnd);
    }

    private static void findNextStates(State state, List<State> nextStates, int listId) {
        if (!state.hasEpsilonTransition()) {
            if (listId == state.getLastListId()) {
                return;
            }
            state.setLastListId(listId);
            nextStates.add(state);
        } else {
            for (int i = 0; i < 2; i++) {
                State next = state.getEpsilonTransitions()[i];
                if (next != null) {
                    findNextStates(next, nextStates, listId);
                }
            }
        }
    }
}
