package com.arch.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusStopResult {
    /**
     * distance: "1162"
     * stopdis: "2"
     * terminal: "沪D-D6643"
     * time: "246"
     */
    //多少米
    private String distance;
    //距离本站
    private Integer stopdis;
    //到站车辆
    private String terminal;
    //需要等待多长时间
    private Integer time;
    //冗余字段
    private String busName;
    private String stopName;
}
