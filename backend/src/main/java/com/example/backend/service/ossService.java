package com.example.backend.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.example.backend.utils.oss.ossConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;

@Service("ossService")
public class ossService {
    public String uploadFile(MultipartFile file) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(ossConstant.END_POINT, ossConstant.ACCESS_KEY_ID, ossConstant.ACCESS_KEY_SECRET);
        String fileName = file.getOriginalFilename();//获取文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//上传文件的后缀名
        String objectName="file";
        String finalFileName = System.currentTimeMillis() + "" + new SecureRandom().nextInt(0x0400) + suffixName;//最后上传生成的文件名
        ObjectMetadata objectMetadata = new ObjectMetadata();// 创建上传文件的元信息，可以通过文件元信息设置HTTP header(设置了才能通过返回的链接直接访问)。
        objectMetadata.setContentType(getContentType(suffixName));
        if(suffixName.equalsIgnoreCase(".jpg")||suffixName.equalsIgnoreCase(".png")||suffixName.equalsIgnoreCase(".jpeg")){
            objectName = "image/" + finalFileName;// "image"是oss中存储图片的文件夹名
            ossClient.putObject(ossConstant.BUCKET, objectName, new ByteArrayInputStream(file.getBytes()),objectMetadata);
        }else if(suffixName.equalsIgnoreCase(".mp4")||suffixName.equalsIgnoreCase(".webm")){
            objectName = "video/" + finalFileName;// "video"是oss中存储视频的文件夹名
            ossClient.putObject(ossConstant.BUCKET, objectName, new ByteArrayInputStream(file.getBytes()));
        }else if(suffixName.equalsIgnoreCase(".wav")||suffixName.equalsIgnoreCase(".mp3")){
            objectName = "audio/" + finalFileName;// "audio"是oss中存储音频的文件夹名
            ossClient.putObject(ossConstant.BUCKET, objectName, new ByteArrayInputStream(file.getBytes()));
        }


        //文件上传

        Date expiration = new Date(System.currentTimeMillis() + (long) (Math.pow(10, 11)));
        String url = ossClient.generatePresignedUrl(ossConstant.BUCKET, objectName, expiration).toString();
        ossClient.shutdown();
        return url;
    }

    public static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp4")) {
            return "video/mp4";
        }
        if (FilenameExtension.equalsIgnoreCase(".webm")) {
            return "video/webm";
        }
        if (FilenameExtension.equalsIgnoreCase(".wav")) {
            return "audio/wav";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp3")) {
            return "audio/mp3";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }

        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }

}
