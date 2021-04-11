package com.arch.service;

import com.arch.dao.model.AlarmSetting;

import java.util.Map;

public interface BusReminderService {
    void updateBusReminder(Map<String, String> map);

    AlarmSetting submitBusInfo(Map<String, String> map);
}
