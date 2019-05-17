package parser;

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

    public void setStart(State start) {
        this.start = start;
    }

    public State getEnd() {
        return end;
    }

    public void setEnd(State end) {
        this.end = end;
    }
}
