package loader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhaoheng on 18/8/15.
 */
public class LoadMain {


    private static JarClassLoader getJarLoader(String jarUrl) throws MalformedURLException {
        return new JarClassLoader(new URL[]{new URL(jarUrl)}, LoadMain.class.getClassLoader());
    }


    public static void main(String[] args) {

    }

}
