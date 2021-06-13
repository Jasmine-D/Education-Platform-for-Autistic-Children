package com.example.backend.controller;

import com.example.backend.controller.json.jsonResult;
import com.example.backend.service.ossService;
import com.example.backend.service.wordCardService;
import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class viewWordCardTest {
    @Resource
    wordCardController myController ;

    public viewWordCardTest() throws IOException {
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
    public void Path1() throws IOException {
        System.out.println("==============Path1 start!===============");

        jsonResult result=myController.searchBySetId(1001,5,3);
        assertEquals( "Search Result is 0", result.getMsg());

        System.out.println("==============Path1 end!===============");
    }

    @org.junit.Test
    public void Path2() {
        System.out.println("==============Path2 start!===============");

        jsonResult result=myController.searchBySetId(1003,1,1);
        assertEquals( "Success", result.getMsg());

        System.out.println("==============Path2 end!===============");
    }

}