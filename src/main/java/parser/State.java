package parser;

public class State {
    private boolean isEnd;
    private State transition;
    private Character symbol;
    private State[] epsilonTransitions = new State[2];

    public State(boolean isEnd) {
        this.isEnd = isEnd;
    }

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

    public void setTransition(State transition, Character symbol) {
        this.transition = transition;
        this.symbol = symbol;
    }

    public void addEpsilonTransition(State epsilonTransition) {
        if (this.epsilonTransitions[0] == null) {
            this.epsilonTransitions[0] = epsilonTransition;
        } else {
            this.epsilonTransitions[1] = epsilonTransition;
        }
    }
}
