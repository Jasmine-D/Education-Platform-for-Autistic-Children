package com.example.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.controller.json.jsonResult;
import com.example.backend.domain.Set_collect;
import com.example.backend.domain.Word_set;
import com.example.backend.service.manageSetService;
import com.example.backend.service.searchSetService;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/wordSet")
@CrossOrigin

//搜索，解锁，收藏，取消收藏
public class wordSetController {

    @Resource
    private searchSetService searchsetService;

    @ApiOperation(value="搜索单词集", notes="根据名字模糊搜索单词集")
    @GetMapping("/SearchByName")
    public jsonResult searchByName (
            @RequestParam("setName") String setName,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("curPage") Integer curPage,
            @RequestParam("userId") Integer userId
    ){
        //将两个service返回的结果合并成一个JSONObject
        List<Word_set> temp=searchsetService.searchResult(setName,pageNum,curPage);
        JSONArray jsonArray=new JSONArray();
        int total=searchsetService.searchResultNum(setName);
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
        jsonObject.put("wordSet",jsonArray);
        jsonObject.put("total",total);
        if(temp.size()==0) {
            return new jsonResult(jsonObject, "Search Result is 0");
        }else {
            return new jsonResult(jsonObject, "Success");
        }
    }

    @ApiOperation(value="所有单词集", notes="展示所有的单词集")
    @GetMapping("/GetAllSet")
    public jsonResult getAllSet (
            @RequestParam("userId") Integer userId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("curPage") Integer curPage
    ){
        //将两个service返回的结果合并成一个JSONObject
        List<Word_set> temp=searchsetService.getAllSet(pageNum,curPage);
        JSONArray jsonArray=new JSONArray();
        int total=searchsetService.allSetNum();
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
        jsonObject.put("allSet",jsonArray);
        jsonObject.put("total",total);
        if(temp.size()==0) {
            return new jsonResult(jsonObject, "Search Result is 0");
        }else {
            return new jsonResult(jsonObject, "Success");
        }
    }

    @ApiOperation(value="解锁单词集", notes="根据密码解锁单词集")
    @GetMapping("/UnlockWordSet")
    public jsonResult unlockWordSet (
            @RequestParam("setId") Integer setId,
            @RequestParam("password") String pwd
    ) {
        JSONObject jsonObject=new JSONObject();
        int isKeyMatch=searchsetService.isKeyMatch(setId, pwd);
        if(isKeyMatch==1){
            jsonObject.put("isKeyMatch",true);
            return new jsonResult(jsonObject, "Pass");
        }else{
            jsonObject.put("isKeyMatch",false);
            return new jsonResult(jsonObject, "Wrong Password");
        }
    }

    @ApiOperation(value="收藏单词集", notes="某用户收藏某单词集")
    @PostMapping ("/StarWordSet")
    public jsonResult starWordSet (
            @RequestParam("userId") Integer userId,
            @RequestParam("setId") Integer setId
    ){
        int starResult=0;
        JSONObject jsonObject=new JSONObject();
        Set_collect set_collect=new Set_collect(userId,setId);

        try {
            starResult = searchsetService.starWordSet(set_collect);
        } catch (Exception e){
            starResult = 0;
            System.out.println("Star Word Set Error");
        } finally {
        }
        if(starResult != 0){
            jsonObject.put("isSucceed",true);
            return new jsonResult(jsonObject, "Pass");
        }else{
            jsonObject.put("isSucceed",false);
            return new jsonResult(jsonObject, "Star Word Set Error");
        }
    }

    @ApiOperation(value="取消收藏单词集", notes="某用户取消收藏某单词集")
    @DeleteMapping ("/NotStarWordSet")
    public jsonResult notStarWordSet (
            @RequestParam Integer userId,
            @RequestParam Integer setId

    ) {
        int notStarResult=0;
        JSONObject jsonObject=new JSONObject();
        try {
            notStarResult = searchsetService.notStarWordSet(userId,setId);
            //sqlSession.commit();
        } catch (Exception e) {
            System.out.println("Cancel Star Word Set Error");
        } finally {
        }

        if(notStarResult == 1){
            jsonObject.put("isSucceed",true);
            return new jsonResult(jsonObject, "Pass");
        }else{
            jsonObject.put("isSucceed",false);
            return new jsonResult(jsonObject, "Cancel Star Word Set Error");
        }
    }

}
