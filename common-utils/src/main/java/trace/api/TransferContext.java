package trace.api;

import java.util.HashMap;

public class TransferContext extends HashMap<String, String> {

    private static final int CONTEXT_SIZE = 50;
    private static final int STRING_LENGTH = 256;

    /**
     * 已PT 或者 INF 为前缀的key可以放入map。其他的key会有大小限制
     * @param key
     * @param value
     * @return
     */
    @Override
    public String put(String key, String value) {
        if (key == null || value == null) {
            if (!Configuration.isIgnoreContextException()) {
                throw new NullPointerException("Context key " + key + ", value " + value);
            }
            return null;
        }
        if (this.size() < CONTEXT_SIZE
                && key.length() <= STRING_LENGTH
                && value.length() <= STRING_LENGTH) {
            return super.put(key, value);
        } else if (ignoreSize(key)) {
            return super.put(key, value);
        } else {
            if (!Configuration.isIgnoreContextException()) {
                throw new ContextOutOfBoundsException("Context size " + this.size() + ", key value " + key + ", value " + value);
            }
        }
        return null;
    }

    /**
     * 为了兼容http协议，key 不区分大小写,以原key优先查询，查询不到会查一遍转化成小写的key
     */
    @Override
    public String get(Object key) {
        String r = super.get(key);
        // 兼容HTTP协议，HTTP
        if (r == null && key != null && key instanceof String) {
            r = super.get(key.toString().toLowerCase());
        }
        return r;
    }

    private boolean ignoreSize(String key) {
        if (key.startsWith("PT") || key.startsWith("pt") || key.startsWith("INF") || key.startsWith("inf") ||
                key.startsWith("gray-release") || key.startsWith("auth")) {
            return true;
        }
        return false;
    }
}
