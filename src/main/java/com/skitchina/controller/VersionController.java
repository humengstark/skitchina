package com.skitchina.controller;

import com.skitchina.mapper.AndroidAppMapper;
import com.skitchina.model.AndroidApp;
import com.skitchina.model.ReturnResult;
import com.skitchina.utils.AddUsers;
import com.skitchina.utils.ReturnResultUtil;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String a = request.getParameter("a");
        AndroidApp androidApp = androidAppMapper.getLastAndroidApp();
        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(androidApp);
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 文件上传页面
     *
     * @param response
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/commitVersion.jsp");
    }

    /**
     * 获取上传的文件版本，名字，序号
     * @param request
     * @param response
     */
    @RequestMapping(value = "/commitVersion", method = RequestMethod.GET)
    public void commitVersion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id").trim());
        String version = request.getParameter("version").trim();
        String name = request.getParameter("name").trim();

        if (androidAppMapper.getAndroidAppById(id) != null) {
            androidAppMapper.deleteAndroidApp(id);
        }

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());

        Map params = new HashMap();
        params.put("id", id);
        params.put("version", version);
        params.put("name", name);
        params.put("time", time);

        androidAppMapper.addAndroidApp(params);

        response.sendRedirect(request.getContextPath()+"/upload.jsp");
    }
    /**
     * 文件上传
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/uploadApp", method = RequestMethod.POST)
    public void uploadApp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AndroidApp androidApp = androidAppMapper.getLastAndroidApp();
        String version = androidApp.getVersion();
        //服务器配置
//        String url = "http://123.206.24.66:8686/androidApp/" + androidApp.getName() + "-" + version + ".apk";
        //笔记本配置
        String url = "D:/androidApp/" +androidApp.getName()+"-"+ version + ".apk";
        //台式机配置
//        String url = "E:/Download/" +androidApp.getName()+"-"+ version + ".apk";
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
                    //笔记本配置
                    File dest = new File(url);
                    FileOutputStream fileOutputStream = new FileOutputStream(dest);
                        /*对上传文件的输入流进行处理，跟本地的文件流处理方式相同*/
                    byte[] buf = new byte[1024];
                    int bytesRead;
                    try {
                        while ((bytesRead = srcinInputStream.read(buf)) > 0) {
                            fileOutputStream.write(buf, 0, bytesRead);
                        }
                    }finally {
                        fileOutputStream.close();
                        srcinInputStream.close();
                }
                }
            }
        }
        //台式机配置
//        String url2 = "http://123.206.24.66:8686/androidApp/" + androidApp.getName() + "-" + androidApp.getVersion() + ".apk";
        //笔记本配置
        String url2 = "http://123.206.24.66:8686/androidApp/" + androidApp.getName() + "-" + androidApp.getVersion() + ".apk";
        Map params = new HashMap();
        params.put("id", androidApp.getId());
        params.put("url", url2);
        androidAppMapper.updateAppUrl(params);
        response.sendRedirect(request.getContextPath()+"/uploadsuccess.jsp");
    }

    /**
     * 下载App
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/downloadAndroidApp", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String downloadAndroidApp() {
        AndroidApp androidApp = androidAppMapper.getLastAndroidApp();
        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(androidApp);
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 增加假数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addUser", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public String addUser() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i=0;i<10000;i++) {
            String passport = AddUsers.getTel();
            String password = "123456";
            String companyname = AddUsers.getName();
            String activated_at = simpleDateFormat.format(AddUsers.randomDate("2015-07-05", "2017-09-23"));
            String created_at = activated_at;

            Map params = new HashMap();
            params.put("passport", passport);
            params.put("password", password);
            params.put("companyname", companyname);
            params.put("activated_at", activated_at);
            params.put("created_at", created_at);

            androidAppMapper.addUser(params);
        }

        return "add success!!!";
    }
}
