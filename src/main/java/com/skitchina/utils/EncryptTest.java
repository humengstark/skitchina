package com.skitchina.utils;


import org.apache.commons.codec.binary.Base64;

import java.util.Map;

/**
 * Created by hu meng on 2017/6/28.
 */
public class EncryptTest {
    public static void main(String[] args) throws Exception {
        //生成密钥对
        Map<String, Object> senderKeyMap = DHEncryptUtil.initKey();
        byte[] senderPublicKey = DHEncryptUtil.getPublicKey(senderKeyMap);
        byte[] senderPrivateKey = DHEncryptUtil.getPrivateKey(senderKeyMap);
        System.out.println("发送方的公钥："+ Base64.encodeBase64String(senderPublicKey));
        System.out.println("发送放的私钥：" + Base64.encodeBase64String(senderPrivateKey));

        //根据发送方公钥生成接收方密钥对
        Map<String, Object> receiverKeyMap = DHEncryptUtil.initKey(senderPublicKey);
        byte[] receiverPublicKey = DHEncryptUtil.getPublicKey(receiverKeyMap);
        byte[] receiverPrivateKey = DHEncryptUtil.getPrivateKey(receiverKeyMap);
        System.out.println("接收方的公钥：" + Base64.encodeBase64String(receiverPublicKey));
        System.out.println("接收方的私钥：" + Base64.encodeBase64String(receiverPrivateKey));

        //构建密钥
        byte[] secrectKey1 = DHEncryptUtil.getSecretKey(senderPublicKey, receiverPrivateKey);
        byte[] secrectKey2 = DHEncryptUtil.getSecretKey(receiverPublicKey, senderPrivateKey);

        System.out.println("发送方密钥：" + Base64.encodeBase64String(secrectKey1));
        System.out.println("接收方密钥：" + Base64.encodeBase64String(secrectKey2));
    }
}
