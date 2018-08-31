package trace.api;

public class ContextOutOfBoundsException extends RuntimeException{

    private static final long serialVersionUID = 6503985310260592053L;

    public ContextOutOfBoundsException() {
        super();
    }
    public ContextOutOfBoundsException(String s) {
        super(s);
    }
}
