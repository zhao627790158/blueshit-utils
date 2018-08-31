package trace.api;

public class Endpoint {
    private String appkey;
    private String host;
    private int port;

    public Endpoint(String appkey, String host, int port) {
        this.appkey = appkey;
        this.host = host;
        this.port = port;
    }

    public String getAppkey() {
        return appkey;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setAppkey(String appkey) {
        if (appkey != null)
            this.appkey = appkey;
    }

    public void setHost(String host) {
        if (host != null)
            this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void copy(Endpoint endpoint) {
        if (endpoint != null) {
            setAppkey(endpoint.getAppkey());
            setHost(endpoint.getHost());
            this.port = endpoint.getPort();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Endpoint");
        sb.append("{appkey='").append(appkey).append('\'');
        sb.append(", host='").append(host).append('\'');
        sb.append(", port=").append(port);
        sb.append('}');
        return sb.toString();
    }
}
