package parser;

/**
 * Represents a nondeterministic finite automaton (NFA). Only has references
 * to the start and end states of the NFA.
 */
public class NFA {
    private State start;
    private State end;

    public NFA(State start, State end) {
        this.start = start;
        this.end = end;
    }

    public State getStart() {
        return start;
    }

    public State getEnd() {
        return end;
    }
}
