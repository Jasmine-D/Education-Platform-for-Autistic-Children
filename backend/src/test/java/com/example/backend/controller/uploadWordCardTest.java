package com.example.backend.controller;


import com.example.backend.controller.json.jsonResult;
import org.apache.http.entity.ContentType;

import org.apache.commons.fileupload.FileItem;


import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class uploadWordCardTest {
    @Resource
    wordCardController myController;

    //音频
    File audiofile = new File("C:\\Users\\qing\\Desktop\\audio.m4a");
    FileItem fileItem = this.getMultipartFile(audiofile,"templFileItem");
    MultipartFile audioFile = new CommonsMultipartFile(fileItem);

    //图像
    File picfile = new File("C:\\Users\\qing\\Desktop\\picture.jpg");
    FileInputStream pinput = new FileInputStream(picfile);
    MultipartFile picFile = new MockMultipartFile("file", picfile.getName(), "text/plain", pinput);


    public uploadWordCardTest() throws IOException {
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

        jsonResult result=myController.uploadWordCard(1003,"test1","content",picFile,picFile);
       assertEquals("Upload or Update Error",result.getMsg());

        System.out.println("==============Path1 end!===============");
    }

    @org.junit.Test
    public void Path2() throws IOException {
        System.out.println("==============Path2 start!===============");

        jsonResult result=myController.uploadWordCard(1001,"test2","content",audioFile,picFile);
        assertEquals("Upload or Update Error", result.getMsg());

        System.out.println("==============Path2 end!===============");
    }

    @org.junit.Test
    public void Path3() throws IOException {
        System.out.println("==============Path3 start!===============");

        jsonResult result=myController.uploadWordCard(1003,"test3","content",audioFile,picFile);
        assertEquals("Success", result.getMsg());

        System.out.println("==============Path3 end!===============");
    }


    /**
     * 将file转换成fileItem
     * @param file
     * @param fieldName
     * @return
     */
    private FileItem  getMultipartFile(File file, String fieldName){
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(fieldName, "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

}