package com.skitchina.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skitchina.mapper.AdvertisementMapper;
import com.skitchina.model.Advertisement;
import com.skitchina.model.ReturnResult;
import com.skitchina.utils.FileUploadUtil;
import com.skitchina.utils.ReturnResultUtil;
import com.skitchina.utils.Utils;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

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

    /**
     * 获取首页三张图片
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getSimpleIntroduce2",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String getSimpleIntroduce2()throws Exception {
        List<Advertisement> advertisementList = advertisementMapper.getSimpleIntroduce2();
        Set<String> results = new HashSet<String>();

        for (Advertisement advertisement : advertisementList) {
            results.add(advertisement.getFirstImgPath());
        }

        return Utils.returnEncrypt(new ReturnResult(0, 0, "", results));
    }

    /**
     * 获取所有商家
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = ("/getAllAdvertisements"),produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String getAllAdvertisements()throws Exception {
        List<Advertisement> advertisementList = advertisementMapper.getAllAdvertisements();
        List<Map> result = new ArrayList<>();

        for (Advertisement advertisement : advertisementList) {
            Map map = new HashMap();
            Map map2 = new HashMap();

            map2.put("top_picture", advertisement.getSecondImgPath());
            map2.put("others_picture", advertisement.getOtherImgsPath());

            map.put("advertisement",advertisement);
            map.put("url", map2);
            result.add(map);
        }

        return Utils.returnEncrypt(new ReturnResult(0, 0, "", result));
    }

    /**
     * 获取单个商家详情
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getAdvertisementById", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getAdvertisementById(HttpServletRequest request)throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);
        int ad_id = jsonObject.getIntValue("ad_id");
        Advertisement advertisement = advertisementMapper.getAdvertisementById(ad_id);

        Map map = new HashMap();
        map.put("top_picture", advertisement.getSecondImgPath());
        map.put("other_picture", advertisement.getOtherImgsPath());
        Map result = new HashMap();
        result.put("advertisement", advertisement);
        result.put("url", map);

        return Utils.returnEncrypt(new ReturnResult(0, 0, "", result));
    }

}
