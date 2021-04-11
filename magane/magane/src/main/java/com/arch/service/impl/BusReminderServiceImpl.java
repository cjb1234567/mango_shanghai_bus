package com.arch.service.impl;

import com.arch.dao.model.AlarmSetting;
import com.arch.pojo.BusRoute;
import com.arch.pojo.BusStopRequest;
import com.arch.repository.AlarmSettingRepository;
import com.arch.service.BusReminderService;
import com.arch.service.BussinessException;
import com.arch.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Slf4j
public class BusReminderServiceImpl implements BusReminderService {
    @Autowired
    private AlarmSettingRepository alarmSettingRepository;

    private static Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

    @Autowired
    private CommonService commonService;

    @Override
    public void updateBusReminder(Map<String, String> updateMap) {

        String busNo = updateMap.get("busNo");
        if (busNo.isEmpty()) {
            throw new BussinessException("busNo 不能为空");
        }
        String direction = updateMap.get("direction");
        String deptStop = updateMap.get("deptStop");
        String deptStopName = updateMap.get("deptStopName");
        String deptTime = updateMap.get("deptTime");
        String startAlarm = updateMap.get("startAlarm");
        String daySwitch = updateMap.get("daySwitch");
        if (direction.isEmpty() || !isInteger(direction)) {
            throw new BussinessException("direction 值设置错误");
        }
        if (deptStop.isEmpty()) {
            throw new BussinessException("出发站 设置错误");
        }
        if (deptStopName.isEmpty()) {
            throw new BussinessException("出发站1 不能为空");
        }
        if (deptTime.isEmpty()) {
            throw new BussinessException("出发时间 不能为空");
        }
        if (startAlarm.isEmpty()) {
            throw new BussinessException("开始预警时间 不能为空");
        }
        if (daySwitch.isEmpty()) {
            throw new BussinessException("选择模式 不能为空");
        }
        AlarmSetting alarmSetting = new AlarmSetting();
        alarmSetting.setId(1);
        alarmSetting.setHardwareSno("chenjinbo");
        alarmSetting.setBusNo(busNo);

        //需要调用接口
        alarmSetting.setBusSid("");
        alarmSetting.setDirection(0);
        alarmSetting.setDeptStop("");
        alarmSetting.setDeptStopName("");
        alarmSetting.setDeptTime(getTime(deptTime));
        alarmSetting.setStartAlarm(getTime(startAlarm));
        alarmSetting.setDaySwitch("1,1,1");
        alarmSettingRepository.update(alarmSetting);
    }

    @Override
    public AlarmSetting submitBusInfo(Map<String, String> updateMap) {
        String busRoute = updateMap.get("busRoute");
        if (busRoute.isEmpty()) {
            throw new BussinessException("busNo 不能为空");
        }
        String direction = updateMap.get("direction");
        String deptStop = updateMap.get("deptStop");
        String deptTime = updateMap.get("deptTime");
        String startAlarm = updateMap.get("startAlarm");
        if (direction.isEmpty() || !isInteger(direction)) {
            throw new BussinessException("direction 值设置错误");
        }
        if (deptStop.isEmpty() || !isInteger(deptStop)) {
            throw new BussinessException("出发站 设置错误");
        }
        if (deptTime.isEmpty()) {
            throw new BussinessException("出发时间 不能为空");
        }
        if (startAlarm.isEmpty()) {
            throw new BussinessException("开始预警时间 不能为空");
        }
        Integer requestDirection = Integer.parseInt(direction);
        BusRoute busRouteInfo = commonService.getSidInfo(busRoute);
        List<BusStopRequest> busStopRequests = commonService.getBusStopInfo(busRouteInfo.getSid(), requestDirection);
        AlarmSetting alarmSetting = new AlarmSetting();
        alarmSetting.setId(1);
        alarmSetting.setHardwareSno("mobile_1");
        alarmSetting.setBusNo(busRoute);

        //需要调用接口
        alarmSetting.setBusSid(busRouteInfo.getSid());
        alarmSetting.setDeptStopName(getDeptStopName(Integer.parseInt(deptStop), busStopRequests));
        alarmSetting.setDirection(Integer.parseInt(direction));
        alarmSetting.setDeptStop(deptStop);
        alarmSetting.setDeptTime(getTime(deptTime));
        alarmSetting.setStartAlarm(getTime(startAlarm));
        alarmSetting.setDaySwitch("1,1,1");
        alarmSettingRepository.update(alarmSetting);
        return alarmSetting;
    }

    public String getDeptStopName(Integer deptStopId, List<BusStopRequest> busStopRequests) {
        for (BusStopRequest busStopRequest : busStopRequests) {
            if (busStopRequest.getStopid().equals(deptStopId)) {
                return busStopRequest.getStopName();
            }
        }
        return "";
    }

    public static boolean isInteger(String str) {
        return pattern.matcher(str).matches();
    }

    public static int getTime(String value) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(new Date());
        String newTime = time + " " + value + ":00";
        System.out.println(newTime);
        SimpleDateFormat newFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long result = 0L;
        try {
            result = newFormatter.parse(newTime).getTime();
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return (int) (result / 1000);
    }
}
