package trace.api;


public class NullConfig implements IConfig {
    @Override
    public boolean isSample(Span span) {
        return false;
    }

    @Override
    public String getLocalSwimlane() {
        return null;
    }

    @Override
    public String get(String key) {
        return null;
    }
}
