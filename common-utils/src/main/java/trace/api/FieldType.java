package trace.api;

public enum FieldType {
    TraceId("M-TraceId"),
    SpanId("M-SpanId"),
    Appkey("M-Appkey"),
    SpanName("M-SpanName"),
    Annotation("M-Annotation"),
    Sample("M-Sample"),
    Debug("M-Debug"),
    Host("M-Host"),
    Port("M-Port"),
    TransferContextPre("M-TransferContext-"),
    TransferOneContextPre("M-TransferOneStep-");


    private final String name;

    FieldType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
