package com.skitchina.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skitchina.mapper.AdvertisementMapper;
import com.skitchina.model.Advertisement;
import com.skitchina.model.ReturnResult;
import com.skitchina.utils.FileUploadUtil;
import com.skitchina.utils.ReturnResultUtil;
import com.skitchina.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/27.
 */
@Controller
@RequestMapping(value = "/advertisement")
public class AdvertisementController {

    @Autowired
    @Qualifier("advertiseMapperImpl")
    private AdvertisementMapper advertisementMapper;

    /**
     * 上传多个图片
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/photosUpload", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String photosUpload(HttpServletRequest request) throws IOException {

        int client_id = Integer.parseInt(request.getParameter("client_id"));
        String simpleIntroduce = request.getParameter("simpleIntroduce").trim();
        String introduce = request.getParameter("introduce").trim();

        Advertisement advertisement = advertisementMapper.getAdvertisementByClientId(client_id);
        if (advertisement != null) {
            advertisementMapper.deleteAdvertisementByClientId(client_id);
        }

        int imgNum = FileUploadUtil.filesUpload(request);

        Map params = new HashMap();
        params.put("client_id", client_id);
        params.put("simpleIntroduce", simpleIntroduce);
        params.put("introduce", introduce);
        params.put("imgNum", imgNum);

        advertisementMapper.addAdvertisement(params);

        ReturnResult returnResult = new ReturnResult(0);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 获取简介
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getSimpleIntroduce", produces = "application/json;charset-utf-8", method = RequestMethod.POST)
    public String getSimpleIntroduce(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int client_id = jsonObject.getIntValue("client_id");
        Advertisement advertisement = advertisementMapper.getAdvertisementByClientId(client_id);

        Map result = new HashMap();
        result.put("simpleIntroduce", advertisement.getSimpleIntroduce());
        result.put("path", advertisement.getFirstImgPath());

        ReturnResult returnResult = new ReturnResult(0, 0, "", result);

        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 获取详细介绍
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getIntroduce", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getIntroduce(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int client_id = jsonObject.getIntValue("client_id");
        Advertisement advertisement = advertisementMapper.getAdvertisementByClientId(client_id);

        Map result = new HashMap();
        result.put("introduce", advertisement.getIntroduce());
        result.put("path", advertisement.getSecondImgPath());

        ReturnResult returnResult = new ReturnResult(0, 0, "", result);

        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 获取其他图片
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOtherImgsPath", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getOtherImgsPath(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int client_id = jsonObject.getIntValue("client_id");
        Advertisement advertisement = advertisementMapper.getAdvertisementByClientId(client_id);

        List<String> paths = advertisement.getOtherImgsPath();

        ReturnResult returnResult = new ReturnResult(0, 0, "", paths);

        return Utils.returnEncrypt(returnResult);
    }

}
