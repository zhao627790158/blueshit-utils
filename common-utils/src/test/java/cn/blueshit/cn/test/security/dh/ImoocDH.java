package cn.blueshit.cn.test.security.dh;

import com.google.common.base.Objects;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;


public class ImoocDH {
	
	private static String src = "imooc security dh";

	public static void main(String[] args) {
		jdkDH();
	}
	
	public static void jdkDH() {
		try {
			//1.初始化发送方密钥
			KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			senderKeyPairGenerator.initialize(512);
			KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
			byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();//发送方公钥，发送给接收方（网络、文件。。。）
			
			//2.初始化接收方密钥
			KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
			PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
			DHParameterSpec dhParameterSpec = ((DHPublicKey)receiverPublicKey).getParams();//需要从发送方的数据中获取数据来生成自己的秘钥
			KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			receiverKeyPairGenerator.initialize(dhParameterSpec);
			KeyPair receiverKeypair = receiverKeyPairGenerator.generateKeyPair();
			PrivateKey receiverPrivateKey = receiverKeypair.getPrivate();
			byte[] receiverPublicKeyEnc = receiverKeypair.getPublic().getEncoded();
			
			//3.密钥构建
			KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
			receiverKeyAgreement.init(receiverPrivateKey);
			receiverKeyAgreement.doPhase(receiverPublicKey, true);
			SecretKey receiverDesKey = receiverKeyAgreement.generateSecret("DES");
			
			KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
			x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
			PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(senderKeyPair.getPrivate());
			senderKeyAgreement.doPhase(senderPublicKey, true);
			SecretKey senderDesKey = senderKeyAgreement.generateSecret("DES");
			if (Objects.equal(receiverDesKey, senderDesKey)) {
				System.out.println("双方密钥相同");
			}
			
			//4.加密
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("jdk dh encrypt : " + Base64.encodeBase64String(result));
			
			//5.解密
			cipher.init(Cipher.DECRYPT_MODE, receiverDesKey);
			result = cipher.doFinal(result);
			System.out.println("jdk dh decrypt : " + new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
