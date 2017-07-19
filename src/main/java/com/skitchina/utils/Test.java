package com.skitchina.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.skitchina.model.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by hu meng on 2017/4/19.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String str = "CGmR28blCuJnyLch5JhNt4l3iI7J9jYl5EueGszCPRWbYwNcqMH2b6XxLG8c9mspmyuTVWDwUROloZke5h4iaLfUURT1qumBtqaNle9MbCzf4Q8cDgMs3tWr8+fXG07ZMsRDe1glAkdgYrC87t1pq6NlV5YqpGgTEkQRE3RgLng2Yn6lhR8gl1j4KXprjxgYcwCuubJSjoo80/P5sQep+iCVA4JEIjdh3434F2zVzV34o9hjEdXGRCOymkgCd4cCqq7lzhz8qeEX6/xbH9WDfxW4MaP3G4RIQOHbtLT57Fg0a84Lju002hD2qPq7hESU";
        String key = "0000000000000000";
        String str1 = EncipherData.Decrypt(str, key);
        System.out.println(str1);
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
