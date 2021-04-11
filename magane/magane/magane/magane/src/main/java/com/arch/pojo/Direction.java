package com.arch.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Direction {
    private String fromStop;
    private String fromTime;
    private String toStop;
    private String toTime;
    private Integer stopType;
}
