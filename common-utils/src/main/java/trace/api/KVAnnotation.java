package trace.api;

public class KVAnnotation {
    private String key;
    private String value;
    private boolean index;
    private long ts;

    public KVAnnotation(String key, String value) {
        this.key = key;
        this.value = value;
        this.index = true;
        ts = System.currentTimeMillis();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public boolean isIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public long getTs() {
        return ts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("KVAnnotation(");
        sb.append("key:");
        sb.append(key);
        sb.append(", value:");
        sb.append(value);
        sb.append(", index:");
        sb.append(index);
        sb.append(", ts:");
        sb.append(ts);
        sb.append(")");
        return sb.toString();
    }
}