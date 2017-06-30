package com.skitchina.utils;


import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/28.
 */
public class DHEncryptUtil {

    //非对称密钥算法
    public static final String KEY_ALGORITHM="DH";

    //本地密钥算法，即对称加密算法。可选des，aes，desede
    public static final String SECRET_ALGORITHM="AES";

    /**
     * 密钥长度，DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到1024位之间
     * */
    private static final int KEY_SIZE=512;
    //公钥
    private static final String PUBLIC_KEY="DHPublicKey";

    //私钥
    private static final String PRIVATE_KEY="DHPrivateKey";

    /**
     * 初始化甲方密钥
     * @return Map 甲方密钥的Map
     * */
    public static Map<String,Object> initKey() throws Exception{
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair=keyPairGenerator.generateKeyPair();
        //甲方公钥
        DHPublicKey publicKey=(DHPublicKey) keyPair.getPublic();
        //甲方私钥
        DHPrivateKey privateKey=(DHPrivateKey) keyPair.getPrivate();
        //将密钥存储在map中
        Map<String,Object> keyMap=new HashMap<String,Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 初始化乙方密钥
     * @param key 甲方密钥（这个密钥是通过第三方途径传递的）
     * @return Map 乙方密钥的Map
     * */
    public static Map<String,Object> initKey(byte[] key) throws Exception{
        //解析甲方的公钥
        //转换公钥的材料
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);
        //实例化密钥工厂
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
        //产生公钥
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);
        //由甲方的公钥构造乙方密钥
        DHParameterSpec dhParamSpec=((DHPublicKey)pubKey).getParams();
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
        //初始化密钥生成器
        keyPairGenerator.initialize(dhParamSpec);
        //产生密钥对
        KeyPair keyPair=keyPairGenerator.genKeyPair();
        //乙方公钥
        DHPublicKey publicKey=(DHPublicKey)keyPair.getPublic();
        //乙方私钥
        DHPrivateKey privateKey=(DHPrivateKey)keyPair.getPrivate();
        //将密钥存储在Map中
        Map<String,Object> keyMap=new HashMap<String,Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 构建密钥
     * @param publicKey 公钥
     * @param privateKey 私钥
     * @return byte[] 本地密钥
     * */
    public static byte[] getSecretKey(byte[] publicKey,byte[] privateKey) throws Exception{
        //实例化密钥工厂
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(publicKey);
        //产生公钥
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);
        //初始化私钥
        //密钥材料转换
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(privateKey);
        //产生私钥
        PrivateKey priKey=keyFactory.generatePrivate(pkcs8KeySpec);
        //实例化
        KeyAgreement keyAgree= KeyAgreement.getInstance(keyFactory.getAlgorithm());
        //初始化
        keyAgree.init(priKey);
        keyAgree.doPhase(pubKey, true);
        //生成本地密钥
        SecretKey secretKey=keyAgree.generateSecret(SECRET_ALGORITHM);
        return secretKey.getEncoded();
    }

    /**
     * 取得私钥
     * @param keyMap 密钥map
     * @return byte[] 私钥
     * */
    public static byte[] getPrivateKey(Map<String,Object> keyMap){
        Key key=(Key)keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }
    /**
     * 取得公钥
     * @param keyMap 密钥map
     * @return byte[] 公钥
     * */
    public static byte[] getPublicKey(Map<String,Object> keyMap) throws Exception{
        Key key=(Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

}
