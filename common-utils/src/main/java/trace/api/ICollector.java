package trace.api;

public interface ICollector {
    void collect(final Span t);

    void setActive(boolean flag);
}
