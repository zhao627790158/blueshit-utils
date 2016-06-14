package cn.zh.blueshit.security;

import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhaoheng on 2016/6/13.
 */
public class HMACSHA1Utils {

    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final BASE64Encoder base64Encoder = new BASE64Encoder();
    /**
     * 生成签名数据
     *
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @return 生成MD5编码的字符串
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String getSignature(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data);
        return base64Encoder.encode(rawHmac);
    }

}
