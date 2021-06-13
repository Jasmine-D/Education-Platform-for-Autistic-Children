package com.example.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.controller.json.jsonResult;
import com.example.backend.dao.reportRepository;
import com.example.backend.domain.Report;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。

public class reportControllerTest {
    @Resource
    private reportController reportController;
    @Resource
    private reportRepository reportRepository;
    @Before
    public void setUp() throws Exception {
        System.out.println("==============Testing Start!=============");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("===============Testing End!==============");
    }
    @Test
    public void createReportPath1() throws Exception{
        System.out.println("createReportPath1:");
        String teacher="小张";
        int score=5;
        String comment="非常好！";
        int user_id=1012;
        int scene_id=1001;
        jsonResult result=reportController.createReport(user_id,scene_id,score,comment,teacher);
        result.printResult();
    }

    @Test
    public void createReportPath2() throws Exception{
        System.out.println("createReportPath2:");
        String teacher="小张";
        int score=5;
        String comment="非常好！";
        int user_id=1005;
        int scene_id=1010;
        jsonResult result=reportController.createReport(user_id,scene_id,score,comment,teacher);
        result.printResult();
    }

    @Test
    public void createReportPath3() throws Exception{
        System.out.println("createReportPath3:");
        String teacher="小张";
        int score=5;
        String comment="非常好！";
        int user_id=1002;
        int scene_id=1001;
        jsonResult report=reportController.getSomeReport(user_id,scene_id);
        report.printmsg();
        List<Report> reports=reportRepository.querySomeReport(user_id,scene_id);
        jsonResult result=reportController.modifyReport(reports.get(0).getReport_id(),score,comment,teacher);
        result.printResult();
    }

    @Test
    public void createReportPath4() throws Exception{
        System.out.println("createReportPath4:");
        String teacher="小张";
        int score=5;
        String comment="非常好！";
        int user_id=1005;
        int scene_id=1001;
        jsonResult result=reportController.createReport(user_id,scene_id,score,comment,teacher);
        result.printResult();
    }

    @Test
    public void viewReportPath1() throws Exception{
        System.out.println("viewReportPath1:");
        int user_id=1015;
        int scene_id=1001;
        jsonResult result=reportController.getAllReport(user_id);
        result.printmsg();
    }
    @Test
    public void viewReportPath2() throws Exception{
        System.out.println("viewReportPath2:");
        int user_id=1003;
        int scene_id=1001;
        jsonResult result=reportController.getAllReport(user_id);
        result.printmsg();
    }

    @Test
    public void viewReportPath3() throws Exception{
        System.out.println("viewReportPath3:");
        int user_id=1006;
        int scene_id=1002;
        jsonResult result=reportController.getAllReport(user_id);
        result.printmsg();
        jsonResult result2=reportController.getSomeReport(user_id,scene_id);
        result2.printmsg();
    }

    @Test
    public void viewReportPath4() throws Exception{
        System.out.println("viewReportPath4:");
        int user_id=1002;
        int scene_id=1005;
        jsonResult result=reportController.getAllReport(user_id);
        result.printmsg();
        jsonResult result2=reportController.getSomeReport(user_id,scene_id);
        result2.printmsg();
    }

}