package com.skitchina.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/28.
 */
public class FileUploadUtil {

    /**
     * 多个图片上传工具，返回上传的数量
     * @param request
     * @return
     * @throws IOException
     */
    public static int filesUpload(HttpServletRequest request) throws IOException {
        int client_id = Integer.parseInt(request.getParameter("client_id"));

        //创建用户文件夹
        File dirs = new File("D:/clientImgs/" + client_id);
        if (!dirs.exists()) {
            dirs.mkdirs();
        } else {
            File[] files = dirs.listFiles();
            for (File file : files) {
                file.delete();
            }
        }

        //解析上传的文件
        MultipartRequest multipartRequest = (MultipartRequest) request;
        Map<String,MultipartFile> multipartFileMap = multipartRequest.getFileMap();

        int filesNum = 0;
        for (String key : multipartFileMap.keySet()) {
            MultipartFile multipartFile = multipartFileMap.get(key);
            File file = new File(dirs, String.valueOf(filesNum) + ".jpg");
            multipartFile.transferTo(file);
            filesNum++;
        }

        return filesNum;
    }
}
