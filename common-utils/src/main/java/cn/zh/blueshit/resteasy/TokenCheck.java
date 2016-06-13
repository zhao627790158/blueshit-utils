package cn.zh.blueshit.resteasy;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

/**
 * Created by zhaoheng on 2016/6/13.
 */
public class TokenCheck {

    private static final Logger log = LoggerFactory.getLogger(TokenCheck.class);

    public static final String TOKENHEAD = "token";

    private static String token = "";

    private static boolean doCheck = false;

    static {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     */
    public static void init() {
        log.info("TokenCheck Properties init *******************");
        ResourceBundle tokenRB = ResourceBundle
                .getBundle("props/token");
        if (tokenRB != null) {
            String open = tokenRB.getString("token.open");
            if (open != null && open.equals("true")) {
                doCheck = true;
                log.info("doCheck *******************");
            }
            if (doCheck) {
                token = tokenRB.getString("token.value");
            }
        }
    }

    /**
     * 检查token
     *
     * @param tokenFrom
     * @return
     */
    public static boolean check(String tokenFrom) {
        boolean flag = false;
        if (doCheck) {
            if (StringUtils.isNotEmpty(tokenFrom) && tokenFrom.equals(token)) {
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }

    public static String getToken() {
        return token;
    }


}
