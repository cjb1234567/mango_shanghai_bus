package com.arch.dao.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * take_bus
 * @author 
 */
@Data
public class TakeBus implements Serializable {
    private Integer id;

    /**
     * 公交车id
     */
    private Integer busId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 上车的站点
     */
    private Integer stopId;

    /**
     * 上车的方向
     */
    private Integer direction;

    /**
     * 开始提醒时间
     */
    private Date startRemindTime;

    /**
     * 结束提醒时间
     */
    private Date endRemindTime;

    /**
     * 0:工作日 1:每周六 2:每周日
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}