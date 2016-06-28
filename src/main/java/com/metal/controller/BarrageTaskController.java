package com.metal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 弹幕任务相关处理入口
 *
 * Created by phil on 2016/6/28.
 */
@Controller
@RequestMapping("/barrage")
public class BarrageTaskController {

    private static Logger logger = LoggerFactory.getLogger(BarrageTaskController.class);

    @RequestMapping("/b_barrage.html")
    public String BarrageTaskPage(HttpServletRequest request, HttpServletResponse response){
        logger.info("===========begin into barrageTaskList page===========");
        return "barrageTaskList";
    }

}
