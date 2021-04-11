package com.arch.controller;

import com.arch.dao.model.AlarmSetting;
import com.arch.pojo.BusRoute;
import com.arch.pojo.BusStopRequest;
import com.arch.pojo.Direction;
import com.arch.service.BusReminderService;
import com.arch.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class InfoController {
    @Autowired
    private CommonService commonService;

    @Autowired
    private BusReminderService busReminderService;

    @RequestMapping(value = "/bus/route_info", method = RequestMethod.POST)
    public List<Direction> getBusRouteInfo(@RequestParam(value = "busRouteName") String busRouteName) {
        BusRoute busRoute = commonService.getSidInfo(busRouteName);
        return commonService.getDirectionInfo(busRoute.getSid());
    }

    @RequestMapping(value = "/bus/stop_info", method = RequestMethod.POST)
    public List<BusStopRequest> getBusStopInfo(@RequestParam String busRouteName, @RequestParam Integer direction) {
        BusRoute busRoute = commonService.getSidInfo(busRouteName);
        return commonService.getBusStopInfo(busRoute.getSid(), direction);
    }

    @RequestMapping(value = "/bus/info_submit", method = RequestMethod.POST)
    public AlarmSetting update(@RequestParam Map<String, String> updateMap) {
        try {
            AlarmSetting alarmSetting = busReminderService.submitBusInfo(updateMap);
            return alarmSetting;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/bus/get_info", method = RequestMethod.GET)
    public List<AlarmSetting> busInfoGet() {
        return commonService.getAllData();
    }

    @GetMapping("/bus/get_audio/text")
    public String getOrderAudioText() {
        return commonService.getRealtimeBusWaitInfo();
    }

    @GetMapping("/bus/get_audio")
    public void getOrderAudio(HttpServletResponse httpServletResponse) {
        //覆盖写本地文件
        if (!commonService.getRealtimeBusWaitInfoAndStore()) {
            return;
        }
        File file;
        FileInputStream in = null;
        ServletOutputStream out = null;
        try {
            String fileName = "tip.mp3";
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            httpServletResponse.setContentType("Content-Type: audio/mpeg");
            file = ResourceUtils.getFile("/tmp/test.mp3");
            in = new FileInputStream(file);
            out = httpServletResponse.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            log.error("下载文件异常!", e);
        } finally {
            try {
                in.close();
                out.flush();
                out.close();
            } catch (Exception e) {
                log.error("流关闭异常!");
            }
        }
    }
}
