package loader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by zhaoheng on 18/8/15.
 */
public class JarClassLoader extends URLClassLoader {


    static String PREFIX_COMMON_CLASS = "loader.common";

    public JarClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    // common lib class loader to load common class
    private static ClassLoader COMMON_LIB_LOADER;

    public static ClassLoader getCommonLibLoader() {
        if (COMMON_LIB_LOADER == null) {
            synchronized (JarClassLoader.class) {
                if (COMMON_LIB_LOADER == null) {
                    COMMON_LIB_LOADER = JarClassLoader.class.getClassLoader();
                }
            }
        }
        return COMMON_LIB_LOADER;
    }

    private boolean isLibClass(String className) {
        return (className != null && className.startsWith(PREFIX_COMMON_CLASS));
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (isLibClass(name)) {
            try {
                Class<?> aClass = JarClassLoader.getCommonLibLoader().loadClass(name);
                if (null != aClass) {
                    return aClass;
                }
            } catch (Exception e) {

            }
        }
        return super.loadClass(name, resolve);
    }
}
