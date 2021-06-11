package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.controller.json.jsonResult;
import com.example.backend.service.ossService;
import com.example.backend.service.sceneService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping(value="/Scene",produces = "application/json;charset=UTF-8")
@CrossOrigin
public class sceneController {
    @Resource
    private sceneService sceneService;

    @Resource
    private ossService ossService;


    @GetMapping(value = "/getAllScene")
    @ApiOperation(value = "获取所有场景", notes = "获取场景页面的场景")
    public jsonResult getAllScene(
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("user_id") int user_id
            ){
        JSONObject jsonObject=sceneService.getAllScene(currentPage, pageSize,user_id);
        return new jsonResult<>(jsonObject,"获取场景列表成功");
    }

    @GetMapping(value = "/getCollectScene")
    @ApiOperation(value = "获取用户收藏的场景", notes = "获取某个用户收藏的场景")
    public jsonResult getCollectScene(
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("user_id") int user_id
    ){
        JSONObject jsonObject=sceneService.getCollectScene(currentPage, pageSize,user_id);
        return new jsonResult<>(jsonObject,"获取场景收藏列表成功");
    }

    @GetMapping(value = "/getCreatedScene")
    @ApiOperation(value = "获取用户创建的场景", notes = "获取某个用户创建的场景")
    public jsonResult getCreatedScene(
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("user_id") int user_id,
            @RequestParam("creator") String creator
    ){
        JSONObject jsonObject=sceneService.getCreatedScene(currentPage, pageSize,user_id,creator);
        return new jsonResult<>(jsonObject,"获取场景创建列表成功");
    }

    @GetMapping(value = "/searchScene")
    @ApiOperation(value = "查找场景", notes = "根据场景名查找场景")
    public jsonResult getSceneByName(
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("user_id") int user_id,
            @RequestParam("key") String key
    ){
        JSONObject jsonObject=sceneService.getSceneByName(currentPage, pageSize,user_id,key);
        return new jsonResult<>(jsonObject,"搜索场景成功");
    }

    @GetMapping(value = "/collectOrCancel")
    @ApiOperation(value = "收藏或取消收藏", notes = "收藏场景或取消收藏")
    public jsonResult collectOrCancel(
            @RequestParam("user_id") int user_id,
            @RequestParam("scene_id") int scene_id
    ){
        int is_collect=sceneService.collectOrCancel(user_id,scene_id);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("is_collect",is_collect);
        if(is_collect==0){
            jsonObject.put("note","已经取消收藏");
        }else{
            jsonObject.put("note","收藏成功");
        }
        return new jsonResult<>(jsonObject);
    }

    @GetMapping(value="/getScenePic")
    @ApiOperation(value = "获取场景图片", notes = "获取场景图片列表")
    public jsonResult getScenePic(@RequestParam("scene_id") int scene_id){
        JSONObject jsonObject=sceneService.getScenePic(scene_id);
        return new jsonResult<>(jsonObject,"获取场景图片成功");
    }

    @GetMapping(value = "/getSceneInfo")
    @ApiOperation(value="获取场景页面信息",notes="获取场景页面基本信息")
    public jsonResult getSceneInfo(
            @RequestParam("user_id") int user_id,
            @RequestParam("scene_id") int scene_id
    ){
        JSONObject jsonObject=sceneService.getSceneInfo(user_id,scene_id);
        return new jsonResult<>(jsonObject,"获取场景信息成功");
    }

    @GetMapping(value="/getSceneQuestion")
    @ApiOperation(value="获取场景提问",notes="获取场景问答环节问题")
    public jsonResult getSceneQuestion(@RequestParam("scene_id") int scene_id){
        JSONObject jsonObject=sceneService.getSceneQuestion(scene_id);
        return new jsonResult<>(jsonObject,"获取场景问题成功");
    }

    @PostMapping(value = "/setPassword")
    @ApiOperation(value = "设置或修改场景密码",notes="用户设置或修改场景密码")
    public jsonResult setPassword(
            @RequestParam("scene_id") int scene_id,
            @RequestParam("pwd") String pwd
    ){
        sceneService.setPassword(scene_id,pwd);
        return new jsonResult<>(pwd,"设置或修改密码成功！");
    }

    @PostMapping(value="/setPublic")
    @ApiOperation(value = "公开场景",notes="用户公开自己创建的场景")
    public jsonResult setPublic(@RequestParam("scene_id") int scene_id){
        sceneService.setPublic(scene_id);
        return new jsonResult<>("场景公开");
    }

    @PostMapping(value="/deleteScene")
    @ApiOperation(value = "删除场景",notes="用户删除自己创建的场景")
    public jsonResult deleteScene(@RequestParam("scene_id") int scene_id){
        sceneService.deleteScene(scene_id);
        return new jsonResult<>("删除场景成功");
    }

    @PostMapping(value="/createScene",headers="content-type=multipart/form-data")
    @ResponseBody
    @ApiOperation(value="用户创建场景",notes="用户创建新的场景，不带视频")
    public jsonResult createScene(@RequestParam("files1")MultipartFile []files1,@RequestParam("files2")MultipartFile []files2,
                                              @RequestParam("keyword") String keyword,@RequestParam("description") String description,
                                              @RequestParam("content") String content,@RequestParam("scene_name") String scene_name,
                                              @RequestParam("creator") String creator,@RequestParam("is_visible") int is_visible,@RequestParam("is_video") int is_video,
                                              @RequestParam("pwd") String pwd,@RequestParam(value = "video") MultipartFile video) throws IOException {
        for(MultipartFile file1 :files1){
            System.out.println("[file1] - [{}]"+ file1.getOriginalFilename());
        }
        for(MultipartFile file2:files2){
            System.out.println("[file2] - [{}]"+ file2.getOriginalFilename());
        }
        System.out.println("keyword:"+keyword);
        System.out.println("content:"+content);
        System.out.println("description:"+description);
        System.out.println("scene_name:"+scene_name);
        System.out.println("creator:"+creator);
        System.out.println("is_visible:"+is_visible);
        System.out.println("is_video:"+is_video);
        String[] keywords=keyword.split(" ");
        String[] contents=content.split("\\|");
        String[] descriptions=description.split("\\|");
        System.out.println(Arrays.toString(keywords));
        System.out.println(Arrays.toString(descriptions));
        System.out.println(Arrays.toString(contents));
        String coverPic=ossService.uploadFile(files1[0]);
        int scene_id;
        if(is_video==0){
            scene_id=sceneService.insertScene(scene_name,creator,is_visible,pwd,keyword,coverPic,is_video," ");
        }else{
            String video_url=ossService.uploadFile(video);
            scene_id=sceneService.insertScene(scene_name,creator,is_visible,pwd,keyword,coverPic,is_video,video_url);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("scene_id",scene_id);
        sceneService.insertScenePic(scene_id,coverPic,keywords[0],descriptions[0]);
        int scenePicNumber=files1.length;
        int sceneQuestionNumber=files2.length;
        jsonObject.put("scenePicNumber",scenePicNumber);
        jsonObject.put("sceneQuestionNumber",sceneQuestionNumber);
        for(int i=1;i<scenePicNumber;i++){
            String pic_url=ossService.uploadFile(files1[i]);
            sceneService.insertScenePic(scene_id,pic_url,keywords[i],descriptions[i]);
        }
        for(int i=0;i<sceneQuestionNumber;i++){
            String pic_url=ossService.uploadFile(files2[i]);
            sceneService.insertQuestion(scene_id,contents[i],pic_url);
        }
        return new jsonResult<>(jsonObject,"创建场景成功");
    }


}
