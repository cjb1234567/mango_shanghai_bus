package com.arch.dubbo.service.impl;

import com.arch.dubbo.service.DemoService;

public class DemoServiceImpl implements DemoService {
    @Override
    public String getSay() {
        return "Hello World";
    }

    @Override
    public String getNewSay(String value) {
        return value;
    }
}
