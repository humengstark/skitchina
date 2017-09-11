package com.skitchina.utils;

import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.MTPack;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Tony Stark on 2017/9/11.
 */
public class MsgUtil {
    public static void sendSms(String cellphone, String content) {
        Account account = new Account("njckcl@njckcl", "Zzc581Fd");
        PostMsg postMsg = new PostMsg();
        postMsg.getCmHost().setHost("211.147.239.62", 9080);
        try {
            MTPack pack = new MTPack();
            pack.setBatchID(UUID.randomUUID());
            pack.setBatchName("短信测试批次");
            pack.setMsgType(MTPack.MsgType.SMS);

            pack.setBizType(0);
            pack.setDistinctFlag(false);
            ArrayList<MessageData> msgs = new ArrayList<MessageData>();

            /** 单发，一号码一内容 */
            msgs.add(new MessageData(cellphone, content));
            pack.setMsgs(msgs);
            pack.setSendType(MTPack.SendType.MASS);
            postMsg.post(account, pack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getCheckCode() {
        return (int) (Math.random() * (9999 - 1000 + 1)) + 1000;
    }
}
