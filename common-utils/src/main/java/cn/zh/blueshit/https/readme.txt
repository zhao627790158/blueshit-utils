 1、创建java证书，
 C:\> keytool -genkey -alias wangyi -keypass wangyi -keyalg RSA -keysize 1024 -keystore https.keystore -storepass wangyi
                               
 2、将创建的证书保存到C盘（为了方便演示）
 C:\>keytool -export -keystore https.keystore -alias wangyi -file https.crt -storepass wangyi
                             