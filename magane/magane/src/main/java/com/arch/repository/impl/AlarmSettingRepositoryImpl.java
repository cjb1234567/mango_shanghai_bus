package com.arch.repository.impl;

import com.arch.dao.AlarmSettingDao;
import com.arch.dao.model.AlarmSetting;
import com.arch.dao.model.AlarmSettingExample;
import com.arch.repository.AlarmSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class AlarmSettingRepositoryImpl implements AlarmSettingRepository {
    @Autowired
    private AlarmSettingDao alarmSettingDao;

    @Override
    public List<AlarmSetting> getAll() {
        AlarmSettingExample example = new AlarmSettingExample();
        example.createCriteria().andIdGreaterThan(0);
        return alarmSettingDao.selectByExample(example);
    }

    @Override
    public boolean insert(AlarmSetting alarmSetting) {
        int ret = alarmSettingDao.insert(alarmSetting);
        return ret == 1;
    }

    @Override
    public boolean update(AlarmSetting alarmSetting) {
        AlarmSettingExample example = new AlarmSettingExample();
        example.createCriteria().andIdGreaterThan(0);
        int result = alarmSettingDao.deleteByExample(example);
        String info = String.format("数据:%s 插入 delete:%d,  status:%d", alarmSetting.toString(), result, alarmSettingDao.insert(alarmSetting));
        log.info(info);
        return true;
    }
}
