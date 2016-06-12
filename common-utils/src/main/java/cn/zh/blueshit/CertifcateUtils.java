package cn.zhaoheng.test.Rsa;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;

public class CertifcateUtils {

	// 读取证书-包含公钥
	public static byte[] readCertifacates() throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		InputStream in = new FileInputStream("c:/https.crt");
		java.security.cert.Certificate cate = factory.generateCertificate(in);
		return cate.getEncoded();
	}

	// 读取公钥
	public static PublicKey readPublicKeys() throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		InputStream in = new FileInputStream("c:/https.crt");
		java.security.cert.Certificate cate = factory.generateCertificate(in);
		return cate.getPublicKey();
	}

	// 读取私钥
	public static byte[] readPrivateKey() throws Exception {
		KeyStore store = KeyStore.getInstance("JKS");
		InputStream in = new FileInputStream("c:/https.keystore");
		// 需要输入密码:次例为wangyi
		store.load(in, "wangyi".toCharArray());
		// 别名 密码
		PrivateKey pk = (PrivateKey) store.getKey("wangyi",
				"wangyi".toCharArray());
		return pk.getEncoded();
	}

	public static PrivateKey readPrivateKeys() throws Exception {
		KeyStore store = KeyStore.getInstance("JKS");
		InputStream in = new FileInputStream("c:/https.keystore");
		store.load(in, "wangyi".toCharArray());
		PrivateKey pk = (PrivateKey) store.getKey("wangyi",
				"wangyi".toCharArray());
		return pk;
	}

	// 根据字节生成certificate
	public static java.security.cert.Certificate createCertiface(byte b[])
			throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		InputStream in = new ByteArrayInputStream(b);
		java.security.cert.Certificate cate = factory.generateCertificate(in);
		return cate;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

}
