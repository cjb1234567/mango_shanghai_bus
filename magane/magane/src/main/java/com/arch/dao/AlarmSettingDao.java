package com.arch.dao;

import com.arch.dao.model.AlarmSetting;
import com.arch.dao.model.AlarmSettingExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlarmSettingDao {
    long countByExample(AlarmSettingExample example);

    int deleteByExample(AlarmSettingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AlarmSetting record);

    int insertSelective(AlarmSetting record);

    List<AlarmSetting> selectByExample(AlarmSettingExample example);

    AlarmSetting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AlarmSetting record, @Param("example") AlarmSettingExample example);

    int updateByExample(@Param("record") AlarmSetting record, @Param("example") AlarmSettingExample example);

    int updateByPrimaryKeySelective(AlarmSetting record);

    int updateByPrimaryKey(AlarmSetting record);
}