package com.skitchina.controller;

import com.alibaba.druid.support.logging.Log;
import com.skitchina.mapper.AndroidAppMapper;
import com.skitchina.model.AndroidApp;
import com.skitchina.model.ReturnResult;
import com.skitchina.utils.ReturnResultUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsFileUploadSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hu meng on 2017/6/6.
 */
@Controller
@RequestMapping(value = "/version")
public class VersionController {

    @Autowired
    @Qualifier("androidAppMapperImpl")
    private AndroidAppMapper androidAppMapper;

    /**
     * 检查APP版本
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkAppVersion", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String checkAppVersion(HttpServletRequest request) {
        String version = request.getParameter("version");
        AndroidApp androidApp = androidAppMapper.getLastAndroidApp();
        ReturnResult returnResult = new ReturnResult();
        if (!androidApp.getVersion().equals(version)) {
            returnResult.setCode(1);
            returnResult.setDisplay(0);
            returnResult.setMessage("");
            returnResult.setData("");
        } else {
            returnResult.setCode(0);
            returnResult.setDisplay(1);
            returnResult.setMessage(androidApp.getAddress());
            returnResult.setData("");
        }

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 文件上传页面
     *
     * @param response
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/upload.jsp");
    }

    /**
     * 文件上传
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/uploadApp", method = RequestMethod.POST)
    public void uploadApp(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String url = "http://www.jb51.net/article/70412.htm";
        //判断是否是文件上传请求
        if (ServletFileUpload.isMultipartContent(request)) {
            // 创建文件上传处理器
            ServletFileUpload upload = new ServletFileUpload();
            //限制单个上传文件的大小
            upload.setFileSizeMax(1000000 * 20);

            // 解析请求
            FileItemIterator iter = upload.getItemIterator(request);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();

                if (!item.isFormField()) {
                    //是文件上传对象，获取上传文件的输入流
                    InputStream srcinInputStream = item.openStream();
                    File dest = new File(request.getContextPath() + "/resources/static");
                    FileOutputStream fileOutputStream = new FileOutputStream(dest);
                        /*对上传文件的输入流进行处理，跟本地的文件流处理方式相同*/
                    byte[] buf = new byte[1024];
                    int bytesRead;
                    try {
                        while ((bytesRead = srcinInputStream.read(buf)) > 0) {
                            fileOutputStream.write(buf, 0, bytesRead);
                        }
                    }finally {
                        srcinInputStream.close();
                        fileOutputStream.close();
                }
                }
            }
        }
    }
}
