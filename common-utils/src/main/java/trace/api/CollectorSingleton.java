package trace.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CollectorSingleton {

    private static Logger logger = LoggerFactory.getLogger(CollectorSingleton.class);

    private static volatile ICollector collector = null;

    public static ICollector getInstance() {
        if (collector == null) {
            synchronized (CollectorSingleton.class) {
                if (collector == null) {
                    collector = createCollector();
                }
            }
        }
        return collector;
    }

    private static ICollector createCollector() {
        Class<?> clazz = TraceManager.getCollectorClass();
        ICollector collector = null;
        if (clazz != null) {
            try {
                collector = clazz.asSubclass(ICollector.class).newInstance();
                logger.debug("[MTrace] create collector {}", collector.getClass().getName());
                return collector;
            } catch (final Exception e) {
                logger.debug("[MTrace] unable create collector {}, because of {}", collector, e);
            }
        }
        logger.debug("[MTrace] create collector {}", NullCollector.class.getName());
        return new NullCollector();
    }
}
