package com.arch.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusStopRequest {
    private Integer stoptype;
    private Integer stopid;
    private String stopName;
    private String sid;
}
