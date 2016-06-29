package cn.zh.blueshit.security;

import cn.zh.blueshit.security.tool.StringToByteTools;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESUtil {

	/**
	 * 　　* 加密
	 * 
	 * 　　*
	 * 
	 * 　　* @param content 需要加密的内容
	 * 
	 * 　　* @param password 加密密码
	 * 
	 * 　　* @return
	 * 
	 * 　　
	 */

	public static byte[] encrypt(String content, String password) {

		try {

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());

			kgen.init(128, secureRandom);

			SecretKey secretKey = kgen.generateKey();

			byte[] enCodeFormat = secretKey.getEncoded();

			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

			Cipher cipher = Cipher.getInstance("AES");// 创建密码器

			byte[] byteContent = content.getBytes("utf-8");

			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化

			byte[] result = cipher.doFinal(byteContent);

			return result; // 加密

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 解密
	 * 
	 * 　　* @param content 待解密内容
	 * 
	 * 　　* @param password 解密密钥
	 * 
	 * 　　* @return
	 * 
	 * 　　
	 */

	public static byte[] decrypt(byte[] content, String password) {

		try {

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());

			kgen.init(128, secureRandom);

			SecretKey secretKey = kgen.generateKey();

			byte[] enCodeFormat = secretKey.getEncoded();

			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

			Cipher cipher = Cipher.getInstance("AES");// 创建密码器

			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化

			byte[] result = cipher.doFinal(content);

			return result; // 加密

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	public static void main(String[] args) {
		String content = "加密前";

		String password = "d48a29980d1970470009a7f011256f2f";

		// 加密

		System.out.println("加密前：" + content);

		byte[] encryptResult = encrypt(content, password);

		String encryptResultStr = StringToByteTools.parseByte2HexStr(encryptResult);

		System.out.println("加密后：" + encryptResultStr);

		// 解密

		byte[] decryptFrom = StringToByteTools.parseHexStr2Byte(encryptResultStr);

		byte[] decryptResult = decrypt(decryptFrom, password);

		System.out.println("解密后：" + new String(decryptResult));

	}
}
