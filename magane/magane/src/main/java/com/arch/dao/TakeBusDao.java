package com.arch.dao;

import com.arch.dao.model.TakeBus;

public interface TakeBusDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TakeBus record);

    int insertSelective(TakeBus record);

    TakeBus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TakeBus record);

    int updateByPrimaryKey(TakeBus record);
}