package com.arch.service;

import com.alibaba.fastjson.JSONObject;
import com.arch.dao.model.AlarmSetting;
import com.arch.pojo.BusRoute;
import com.arch.pojo.BusStopRequest;
import com.arch.pojo.BusStopResult;
import com.arch.pojo.Direction;
import com.arch.repository.AlarmSettingRepository;
import com.arch.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CommonService {
    @Autowired
    private AlarmSettingRepository alarmSettingRepository;

    public String getRealtimeBusWaitInfo() {
        List<AlarmSetting> alarmSettingList = alarmSettingRepository.getAll();
        if (alarmSettingList.isEmpty()) {
            log.error("当前尚未设置定时提醒,请先设置!");
            return "";
        }
        AlarmSetting alarmSetting = alarmSettingList.get(alarmSettingList.size() - 1);
        if (!judgeExpire(alarmSetting)) {
            log.error("当前时间不在提醒时间范围内,请耐心等候!");
            return "";
        }
        BusStopRequest busStopRequest = new BusStopRequest();
        busStopRequest.setSid(alarmSetting.getBusSid());
        busStopRequest.setStopid(Integer.parseInt(alarmSetting.getDeptStop()));
        busStopRequest.setStopName(alarmSetting.getDeptStopName());
        busStopRequest.setStoptype(alarmSetting.getDirection());
        BusStopResult busStopResult = getBusStopArriveInfo(alarmSetting.getBusNo(), busStopRequest);
        if (busStopResult == null) {
            log.error("当前尚未查询到后台信息,请等候!");
            return "";
        }
        String sound = String.format("%s 车牌号:%s 还有%d站 约%s分钟到达%s 请抓紧时间", busStopResult.getBusName(), busStopResult.getTerminal(), busStopResult.getStopdis(), busStopResult.getTime() / 60, busStopResult.getStopName());
        return sound;
    }

    public boolean getRealtimeBusWaitInfoAndStore() {
        List<AlarmSetting> alarmSettingList = alarmSettingRepository.getAll();
        if (alarmSettingList.isEmpty()) {
            log.error("当前尚未设置定时提醒,请先设置!");
            return false;
        }
        AlarmSetting alarmSetting = alarmSettingList.get(alarmSettingList.size() - 1);
        if (!judgeExpire(alarmSetting)) {
            log.error("当前时间不在提醒时间范围内,请耐心等候!");
            return false;
        }
        BusStopRequest busStopRequest = new BusStopRequest();
        busStopRequest.setSid(alarmSetting.getBusSid());
        busStopRequest.setStopid(Integer.parseInt(alarmSetting.getDeptStop()));
        busStopRequest.setStopName(alarmSetting.getDeptStopName());
        busStopRequest.setStoptype(alarmSetting.getDirection());
        BusStopResult busStopResult = getBusStopArriveInfo(alarmSetting.getBusNo(), busStopRequest);
        if (busStopResult == null) {
            log.error("当前尚未查询到后台信息,请等候!");
            return false;
        }
        RestTemplate restTemplate = new RestTemplate();
        String sound = String.format("%s 车牌号:%s 还有%d站 约%s分钟到达%s 请抓紧时间", busStopResult.getBusName(), busStopResult.getTerminal(), busStopResult.getStopdis(), busStopResult.getTime() / 60, busStopResult.getStopName());
        String url = String.format("https://fanyi.baidu.com/gettts?lan=zh&text=%s&spd=4&source=web", sound);
        // 文件保存的本地路径
        String targetPath = "/tmp/test.mp3";
        File file = new File(targetPath);
        if (file.exists()) {
            file.delete();
        }
        //定义请求头的接收类型
        RequestCallback requestCallback = request -> request.getHeaders()
                .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
        //对响应进行流式处理而不是将其全部加载到内存中
        restTemplate.execute(url, HttpMethod.GET, requestCallback, clientHttpResponse -> {
            Files.copy(clientHttpResponse.getBody(), Paths.get(targetPath));
            return null;
        });
        return true;
    }

    public boolean judgeExpire(AlarmSetting alarmSetting) {
        Long currentTime = System.currentTimeMillis() / 1000;
        if (alarmSetting.getStartAlarm() > currentTime) {
            return false;
        }
        if (alarmSetting.getDeptTime() < currentTime) {
            return false;
        }
        return true;
    }

    /**
     * 获取路线的上行或者下行的站台信息
     *
     * @param sid      fed2ed017700797279389c409b61c2ba
     * @param stoptype
     * @return
     */
    public List<BusStopRequest> getBusStopInfo(String sid, Integer stoptype) {
        List<BusStopRequest> busStopList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(new URI(String.format("https://shanghaicity.openservice.kankanews.com/public/bus/mes/sid/%s?stoptype=%s", sid, 1)), String.class);
            String html = responseEntity.getBody();
            Document doc = Jsoup.parse(html);
            Elements rows = doc.select(".station");
            for (Element element : rows) {
                BusStopRequest busStop = new BusStopRequest();
                busStop.setSid(sid);
                busStop.setStoptype(stoptype);
                String busStopStr = element.select("span").get(0).text();
                busStop.setStopid(Integer.parseInt(busStopStr.substring(0, busStopStr.length() - 1)));
                busStop.setStopName(element.select("span").get(1).text());
                busStopList.add(busStop);
            }
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        return busStopList;
    }


    /**
     * 获取路线的上行或者下行的站台信息
     *
     * @param sid fed2ed017700797279389c409b61c2ba
     * @return
     */
    public List<Direction> getDirectionInfo(String sid) {
        List<Direction> directionList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(new URI(String.format("https://shanghaicity.openservice.kankanews.com/public/bus/mes/sid/%s", sid)), String.class);
            String html = responseEntity.getBody();
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select(".upgoing").select("span");
            Elements elements1 = doc.select(".upgoing").select("em");
            Direction direction = new Direction();
            direction.setFromStop(elements.get(0).text());
            direction.setToStop(elements.get(1).text());
            direction.setFromTime(elements1.get(0).text());
            direction.setToTime(elements1.get(1).text());
            direction.setStopType(0);
            directionList.add(direction);
            Direction converseDirection = new Direction();
            converseDirection.setFromStop(elements.get(2).text());
            converseDirection.setToStop(elements.get(3).text());
            converseDirection.setFromTime(elements1.get(2).text());
            converseDirection.setToTime(elements1.get(3).text());
            converseDirection.setStopType(1);
            directionList.add(converseDirection);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        return directionList;
    }

    /**
     * 获取公交线路的sid信息
     *
     * @param busName
     * @return
     */
    public BusRoute getSidInfo(String busName) {
        String url = "https://shanghaicity.openservice.kankanews.com/public/bus/get";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        JSONObject param = new JSONObject();
        param.put("idnum", busName);
        HttpEntity<String> formEntity = new HttpEntity<String>(param.toJSONString(), headers);
        BusRoute result = restTemplate.postForObject(url, formEntity, BusRoute.class);
        if (result == null) {
            log.error("找不到 对应的sid %s", busName);
            return null;
        }
        result.setBusName(busName);
        return result;
    }


    /**
     * 获取线路公交到站信息
     *
     * @param busName
     * @param busStopRequest
     * @return
     */
    public static BusStopResult getBusStopArriveInfo(String busName, BusStopRequest busStopRequest) {
        String url = "https://shanghaicity.openservice.kankanews.com/public/bus/Getstop";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //提交参数设置
        MultiValueMap<String, String> requestP = new LinkedMultiValueMap<>();
        requestP.add("sid", busStopRequest.getSid());
        requestP.add("stoptyp", busStopRequest.getStoptype().toString());
        requestP.add("stopid", busStopRequest.getStopid().toString());

        //提交请求
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(requestP, httpHeaders);

        String result = restTemplate.postForObject(url, entity, String.class);
        int errorIndex = result.indexOf("error");
        if (errorIndex != -1) {
            log.error("调用到站信息获取失败:result" + result);
            return null;
        }
        List<BusStopResult> results = JsonUtils.fromJson(result, new TypeReference<List<BusStopResult>>() {
        });
        if (results == null) {
            return null;
        }
        for (BusStopResult item : results) {
            item.setStopName(busStopRequest.getStopName());
            item.setBusName(busName);
        }
        return results.get(0);
    }

    public List<AlarmSetting> getAllData() {
        return alarmSettingRepository.getAll();
    }

    public static void main(String[] args) {
        BusStopRequest busStopRequest = new BusStopRequest();
        busStopRequest.setStoptype(1);
        busStopRequest.setStopid(7);
        busStopRequest.setStopName("杨树浦路许昌路");
        busStopRequest.setSid("fed2ed017700797279389c409b61c2ba");

        String result = "{\"erro1\":\"-2\"}";
        System.out.println(result.indexOf("error"));
//        BusStopResult busStopResult = getBusStopArriveInfo("28路", busStopRequest);
//        List<Direction> directions = getDirectionInfo(busStopRequest.getSid());
    }
}
