package com.example.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class reportServiceTest {
    reportService reportService;
    @Before
    public void setUp() throws Exception {
        reportService report=new reportService();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void TestPath1() throws Exception{
        int user_id=1005;
        int scene_id=1001;
        int score=5;
        Date date=new Date();
        String comment="非常好！";
        String teacher="小张";


    }

}