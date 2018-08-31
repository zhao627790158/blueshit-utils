package trace.api;

public class NullCollector implements ICollector {

    @Override
    public void collect(final Span span) {
        // null to do
    }

    @Override
    public void setActive(boolean flag) {
        // null to do
    }
}