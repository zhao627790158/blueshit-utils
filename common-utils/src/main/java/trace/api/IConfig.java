package trace.api;

public interface IConfig {
    boolean isSample(Span span);

    String getLocalSwimlane();

    String get(String key);
}
