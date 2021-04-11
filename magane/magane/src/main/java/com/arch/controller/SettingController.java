package com.arch.controller;

import com.arch.service.BusReminderService;
import com.arch.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

@Controller
@Slf4j
public class SettingController {

    @Autowired
    private BusReminderService busReminderService;

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/setting")
    public String setting() {
        return "bootstrap/setting";
    }
}
