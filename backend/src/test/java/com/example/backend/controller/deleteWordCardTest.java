package com.example.backend.controller;

import com.example.backend.controller.json.jsonResult;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class deleteWordCardTest {

    @Resource
    wordCardController myController=new wordCardController();

    public deleteWordCardTest() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("==============Testing start!===============");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("==============Testing end!===============");
    }

    @org.junit.Test
    public void Path1() {
        System.out.println("==============Path1 start!===============");

        jsonResult result=myController.deleteWordCard(1);
        assertEquals( "Delete or Update Error", result.getMsg());

        System.out.println("==============Path1 end!===============");

    }

    @org.junit.Test
    public void Path2() {
        System.out.println("==============Path2 start!===============");

       jsonResult result=myController.deleteWordCard(1046);
       assertEquals("Success", result.getMsg());

        System.out.println("==============Path2 end!===============");
    }


}