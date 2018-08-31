package trace.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigSingleton {

    private static volatile IConfig config = null;

    public static IConfig getConfig() {
        if (config == null) {
            synchronized (ConfigSingleton.class) {
                if (config == null) {
                    config = createConfig();
                }
            }
        }
        return config;
    }

    private static IConfig createConfig() {
        Class<?> clazz = TraceManager.getConfigClass();
        if (clazz != null) {
            try {
                IConfig config;
                config = clazz.asSubclass(IConfig.class).newInstance();
                return config;
            } catch (final Exception e) {
            }
        }
        return new NullConfig();
    }
}
