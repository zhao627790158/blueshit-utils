package trace.api;

import trace.api.Span;

public class TraceContext {

    private TraceContext() {
    }

    public static Span getCurrentServerSpan() {
        return (Span) Context.getServerContext();
    }

    public static void setCurrentServerSpan(final Span span) {
        if (span == null) {
            Context.removeServerContext();
        } else {
            Context.setServerContext(span);
        }
    }

    public static Span getCurrentClientSpan() {
        return (Span) Context.getClientContext();
    }

    public static void setCurrentClientSpan(final Span span) {
        Context.setClientContext(span);
    }

    @Deprecated
    public static void setDebugFlag(Boolean debugFlag) {

    }

    @Deprecated
    public static Boolean removeDebugFlag() {
        return false;
    }


}
