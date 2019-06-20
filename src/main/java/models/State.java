package models;

/**
 * Represents a state of a NFA.
 * There are two possible kinds of transitions between states: symbol transitions
 * and epsilon transitions. A state can have at most one symbol transition, or two
 * epsilon transitions.
 */
public class State {
    private int lastVisitedId;
    private boolean isEnd;
    private State transition;
    private Character symbol;
    private State[] epsilonTransitions = new State[2];

    public State(boolean isEnd) {
        this.isEnd = isEnd;
    }

    /**
     * @return true if the state is the end for the NFA, otherwise false
     */
    public boolean isEnd() {
        return isEnd;
    }

    public State getTransition() {
        return transition;
    }

    public Character getSymbol() {
        return symbol;
    }

    public State[] getEpsilonTransitions() {
        return epsilonTransitions;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    /**
     * Sets a symbol transition for the state.
     *
     * @param transition destination state
     * @param symbol symbol for the transition
     */
    public void setTransition(State transition, Character symbol) {
        this.transition = transition;
        this.symbol = symbol;
    }

    /**
     * Adds an epsilon transition. It's set as the first epsilon transition for this state if there are no
     * other epsilon transitions, otherwise it's set as the second one.
     *
     * @param epsilonTransition destination state
     */
    public void addEpsilonTransition(State epsilonTransition) {
        if (this.epsilonTransitions[0] == null) {
            this.epsilonTransitions[0] = epsilonTransition;
        } else {
            this.epsilonTransitions[1] = epsilonTransition;
        }
    }

    public boolean hasEpsilonTransition() {
        return this.epsilonTransitions[0] != null;
    }

    /**
     * Returns the ID of the last iteration where this state was visited
     * @return the ID of the iteration
     */
    public int getLastVisitedId() {
        return lastVisitedId;
    }

    /**
     * Set the ID of the last iteration where this state has been visited
     * @param iterationId ID of the iteration
     */
    public void setLastVisitedId(int iterationId) {
        this.lastVisitedId = iterationId;
    }
}
