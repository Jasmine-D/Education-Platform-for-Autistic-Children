package com.example.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.controller.json.jsonResult;
import com.example.backend.domain.Word_set;
import com.example.backend.service.manageSetService;
import com.example.backend.service.searchSetService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value="/myWordSet",produces = "application/json;charset=UTF-8")
@CrossOrigin

//上传，删除，查看我的收藏，查看我自己制作的单词集
public class myWordSetController {

    @Resource
    private manageSetService managesetService;
    @Resource
    private searchSetService searchsetService;


    @ApiOperation(value="上传单词集", notes="创建单词集并提交")
    @PostMapping("/UploadWordSet")
    public jsonResult uploadWordSet (
            @RequestParam int userId,
            @RequestParam String setName,
            @RequestParam String setIntro,
            @RequestParam Boolean isVisible,
            @RequestParam String pwd
    ) {
        int uploadResult = 0;
        int isVsb = 0;
        if(isVisible == true) isVsb=1;
        else isVsb=0;

        JSONObject jsonObject=new JSONObject();
        String setCreator=managesetService.userName(userId);
        Word_set word_set=new Word_set(setName,setCreator,setIntro,isVsb,pwd);
        try {
            uploadResult = managesetService.uploadWordSet(word_set);
        } catch (Exception e){
            uploadResult = 0;
            System.out.println("upload word set error");
        } finally {
        }
        if(uploadResult != 0){
            jsonObject.put("isSucceed",true);
            return new jsonResult(jsonObject, "Pass");
        }else{
            jsonObject.put("isSucceed",false);
            return new jsonResult(jsonObject, "Upload Error");
        }
    }

    @ApiOperation(value="删除单词集", notes="根据指定setId删除单词集")
    @DeleteMapping("/DeleteWordSet")
    public jsonResult deleteWordSet (
            @RequestParam Integer setId
    ) {
        int deleteResult=0;
        JSONObject jsonObject=new JSONObject();
        try {
            deleteResult = managesetService.deleteWordSet(setId);
            //sqlSession.commit();
        } catch (Exception e) {
            System.out.println("delete error");
        } finally {
        }
        if(deleteResult != 0){
            jsonObject.put("isSucceed",true);
            return new jsonResult(jsonObject, "Pass");
        }else{
            jsonObject.put("isSucceed",false);
            return new jsonResult(jsonObject, "Delete Error");
        }
    }

    @ApiOperation(value="我收藏的单词集", notes="查看我收藏的单词集")
    @GetMapping("/MyStarWordSet")
    public jsonResult myStarWordSet (
            @RequestParam Integer userId,
            @RequestParam Integer pageNum,
            @RequestParam Integer curPage
    ) {
        List<Word_set> temp=managesetService.myStarWordSet(userId,pageNum,curPage);
        JSONArray jsonArray=new JSONArray();
        int total=managesetService.myStarWordSetNum(userId);
        for(int i=0;i<temp.size();i++){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("set_id",temp.get(i).getSet_id());
            jsonObject.put("set_name",temp.get(i).getSet_name());
            jsonObject.put("set_creator",temp.get(i).getSet_creator());
            jsonObject.put("set_intro",temp.get(i).getIntroduction());
            if(temp.get(i).getIs_visible()==1){
                jsonObject.put("is_visible",true);
            }else{
                jsonObject.put("is_visible",false);
            }
            jsonObject.put("card_num",temp.get(i).getCard_num());
            int isStar=searchsetService.isStar(userId, temp.get(i).getSet_id());
            if(isStar==1){
                jsonObject.put("is_star",true);
            }else{
                jsonObject.put("is_star",false);
            }
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("myStarWordSet",jsonArray);
        jsonObject.put("total",total);
        if(temp.size()==0) {
            return new jsonResult(jsonObject, "My Star Word Set is 0");
        }else {
            return new jsonResult(jsonObject, "Success");
        }
    }

    @ApiOperation(value="我的单词集", notes="查看我制作的单词集")
    @GetMapping("/MyWordSet")
    public jsonResult myWordSet (
            @RequestParam int userId,
            @RequestParam Integer pageNum,
            @RequestParam Integer curPage
    ) {
        String userName=managesetService.userName(userId);
        List<Word_set> temp=managesetService.myWordSet(userName,pageNum,curPage);
        JSONArray jsonArray=new JSONArray();
        int total=managesetService.myWordSetNum(userName);
        for(int i=0;i<temp.size();i++){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("set_id",temp.get(i).getSet_id());
            jsonObject.put("set_name",temp.get(i).getSet_name());
            jsonObject.put("set_creator",temp.get(i).getSet_creator());
            jsonObject.put("set_intro",temp.get(i).getIntroduction());
            if(temp.get(i).getIs_visible()==1){
                jsonObject.put("is_visible",true);
            }else{
                jsonObject.put("is_visible",false);
            }
            jsonObject.put("card_num",temp.get(i).getCard_num());
            int isStar=searchsetService.isStar(userId, temp.get(i).getSet_id());
            if(isStar==1){
                jsonObject.put("is_star",true);
            }else{
                jsonObject.put("is_star",false);
            }
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("myWordSet",jsonArray);
        jsonObject.put("total",total);
        if(temp.size()==0) {
            return new jsonResult(jsonObject, "My Word Set is 0");
        }else {
            return new jsonResult(jsonObject, "Success");
        }

    }
}
