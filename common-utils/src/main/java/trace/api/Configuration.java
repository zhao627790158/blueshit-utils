package trace.api;

public class Configuration {
    private Configuration() {
    }

    private static final String IGNORE_CONTEXT_EXCEPTION_KEY = "mtrace.ignore.context.exception";
    private static final boolean ignoreContextException = Boolean.getBoolean(IGNORE_CONTEXT_EXCEPTION_KEY);

    public static boolean isIgnoreContextException() {
        return ignoreContextException;
    }
}
