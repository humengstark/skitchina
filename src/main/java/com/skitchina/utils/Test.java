package com.skitchina.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by hu meng on 2017/4/19.
 */
public class Test {
    public static void main(String[] args) throws IOException, WriterException, InterruptedException {

    }

    //�ж��Ƿ�������
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static void createQR() throws WriterException, IOException {
        String filePath = "D://";
        String fileName = "faxisi.png";
        String content = "�����Ƿ���˹�£�������������֣�����";
        int width = 200;
        int height = 200;
        String format = "png";
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix=new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);// ���ɾ���
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// ���ͼ��
        System.out.println("����ɹ�.");
    }
}
