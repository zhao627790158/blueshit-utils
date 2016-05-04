package cn.zh.blueshit.transcodes;

import java.io.Closeable;

/**
 * Created by zhaoheng on 2016/5/4.
 */
public class CloseUtil {
    private CloseUtil() {
    }

    public static void close(Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (Exception var2) {
            }
        }

    }

}
