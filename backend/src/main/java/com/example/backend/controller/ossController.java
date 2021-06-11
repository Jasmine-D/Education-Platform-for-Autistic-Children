package com.example.backend.controller;

import com.example.backend.controller.json.jsonResult;
import com.example.backend.service.ossService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping(value="/Oss",produces = "application/json;charset=UTF-8")
@CrossOrigin
public class ossController {
    @Resource
    private ossService ossService;

    @PostMapping(value = "/Upload",headers="content-type=multipart/form-data")
    @ApiOperation(value = "上传图片", notes = "测试上传")
    public jsonResult ossUpload(
            @RequestParam(value="file") MultipartFile file
    ) throws IOException {
        System.out.println("[文件类型] - [{}]"+ file.getContentType());
        System.out.println("[文件名称] - [{}]"+ file.getOriginalFilename());
        System.out.println("[文件大小] - [{}]"+ file.getSize());
        String url=ossService.uploadFile(file);
        return new jsonResult<>(url,"图片上传成功");
    }
}
