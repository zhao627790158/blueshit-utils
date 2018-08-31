package trace.api;

public class Context {
    private static final ThreadLocal<Object> SERVER_CONTEXT = new ThreadLocal();
    private static final ThreadLocal<Object> CLIENT_CONTEXT = new ThreadLocal();

    public Context() {
    }

    public static Object getServerContext() {
        return SERVER_CONTEXT.get();
    }

    public static Object getClientContext() {
        return CLIENT_CONTEXT.get();
    }

    public static void setServerContext(Object obj) {
        SERVER_CONTEXT.set(obj);
    }

    public static void setClientContext(Object obj) {
        CLIENT_CONTEXT.set(obj);
    }

    public static void removeServerContext() {
        SERVER_CONTEXT.remove();
    }

    public static void removeClientContext() {
        CLIENT_CONTEXT.remove();
    }
}
