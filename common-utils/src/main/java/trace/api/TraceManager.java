package trace.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class TraceManager {

    private static Logger logger = LoggerFactory.getLogger(TraceManager.class);

    protected static final String resource = "META-INF/mtrace-api.properties";

    private static final String IS_COLLECT = "mtrace.collect";

    private static String collectorClass = null;
    private static String configClass = null;
    private static boolean isCollect = true;

    protected TraceManager() {
    }

    static {
        isCollect = Boolean.parseBoolean(System.getProperty(IS_COLLECT, "true"));
        for (Properties p : findResources(resource)) {
            String collectorTemp = p.getProperty("mtrace.collector.class");
            if (null != collectorTemp) {
                collectorClass = collectorTemp;
            }
            String configTemp = p.getProperty("mtrace.config.class");
            if (null != configTemp) {
                configClass = configTemp;
            }
        }
    }

    public static Class<?> getCollectorClass() {
        return loadClass(collectorClass);
    }

    public static Class<?> getConfigClass() {
        return loadClass(configClass);
    }

    private static List<Properties> findResources(String resource) {
        final ClassLoader[] candidates = {
                TraceManager.class.getClassLoader(),
                Thread.currentThread().getContextClassLoader(),
                ClassLoader.getSystemClassLoader()
        };
        List<Properties> propertiesList = new LinkedList<Properties>();
        for (final ClassLoader cl : candidates) {
            if (cl != null) {
                try {
                    final Enumeration<URL> resourceEnum = cl.getResources(resource);
                    while (resourceEnum.hasMoreElements()) {
                        URL url = resourceEnum.nextElement();
                        logger.debug("TraceManager resources url : {}, cl : {}", url, cl);
                        Properties property = new Properties();
                        InputStream is = null;
                        try {
                            is = url.openStream();
                            property.load(is);
                            propertiesList.add(property);
                        } finally {
                            if (is != null) {
                                is.close();
                            }
                        }
                    }
                } catch (final IOException e) {
                    logger.debug("Find resources {} exception", resource, e);
                }
            }
        }
        return propertiesList;
    }

    private static Class<?> loadClass(String className) {
        Class<?> clazz = null;
        if (className == null || className.isEmpty()) {
            return null;
        }

        try {
            clazz = Class.forName(className);
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl != null) {
                clazz = cl.loadClass(className);
            }
        } catch (ClassNotFoundException e) {
            logger.warn("Can not find class {} ", className, e);
        }
        return clazz;
    }

    public static boolean isCollect() {
        return isCollect;
    }
}