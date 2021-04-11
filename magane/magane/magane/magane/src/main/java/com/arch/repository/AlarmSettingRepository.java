package com.arch.repository;

import com.arch.dao.model.AlarmSetting;

import java.util.List;

public interface AlarmSettingRepository {
    List<AlarmSetting> getAll();

    boolean insert(AlarmSetting alarmSetting);

    boolean update(AlarmSetting alarmSetting);
}
