package com.example.backend.controller;

import com.example.backend.controller.json.jsonResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。

public class reportControllerTest {
    @Resource
    private reportController reportController;
    @Before
    public void setUp() throws Exception {
        System.out.println("==============Testing Start!=============");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("===============Testing End!==============");
    }

    @Test
    public void TestPath1() throws Exception{
        int user_id=1005;
        int scene_id=1001;
        int score=5;
        String comment="非常好！";
        String teacher="小张";
        jsonResult result=reportController.createReport(user_id,scene_id,score,comment,teacher);
        result.printResult();
    }

    @Test
    public void TestPath2() throws Exception{

    }

    @Test
    public void TestPath3() throws Exception{

    }

    @Test
    public void TestPath4() throws Exception{

    }
}