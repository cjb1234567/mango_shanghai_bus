package com.arch.dao.model;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * alarm_setting
 * @author 
 */
@Data
@ToString
public class AlarmSetting implements Serializable {
    private Integer id;

    /**
     * 硬件序列号，唯一标识
     */
    private String hardwareSno;

    /**
     * 公交车路线名称，例如156路
     */
    private String busNo;

    /**
     * 公交车SID
     */
    private String busSid;

    /**
     * 方向 0 或者 1
     */
    private Integer direction;

    /**
     * 出发站标号 如12
     */
    private String deptStop;

    /**
     * 出发站名称 如交通大学站
     */
    private String deptStopName;

    /**
     * 出发时间，如8:00
     */
    private Integer deptTime;

    /**
     * 开始提醒时间 如7:20
     */
    private Integer startAlarm;

    /**
     * 工作日，周六，周日是否提醒，用逗号分隔 0否 1 是 如1,0,1
     */
    private String daySwitch;

    private static final long serialVersionUID = 1L;
}