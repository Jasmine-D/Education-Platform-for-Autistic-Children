package com.example.backend.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.controller.json.jsonResult;
import com.example.backend.domain.Word_card;
import com.example.backend.domain.Word_set;
import com.example.backend.service.ossService;
import com.example.backend.service.wordCardService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/wordCard")
@CrossOrigin
public class wordCardController {

    @Resource
    private wordCardService wordcardService;
    @Resource
    private ossService ossService;

    @ApiOperation(value="搜索单词卡", notes="根据指定setId查询单词卡")
    @GetMapping("/SearchBySetId")
    public jsonResult searchBySetId (
            @RequestParam("setId") int setId,
            @RequestParam("pageNum") int pageNum,
            @RequestParam("curPage") int curPage
    ) {
        List<Word_card> temp=wordcardService.wordCard(setId,pageNum,curPage);
        JSONArray jsonArray=new JSONArray();
        int total=wordcardService.wordCardNum(setId);
        for(int i=0;i<temp.size();i++){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("card_id",temp.get(i).getCard_id());
            jsonObject.put("set_id",temp.get(i).getSet_id());
            jsonObject.put("card_name",temp.get(i).getCard_name());
            jsonObject.put("card_content",temp.get(i).getCard_content());
            jsonObject.put("audio_url",temp.get(i).getAudio_url());
            jsonObject.put("pic_url",temp.get(i).getPic_url());
            jsonArray.add(jsonObject);

        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("wordCard",jsonArray);
        jsonObject.put("total",total);
        if(temp.size()==0) {
            return new jsonResult(jsonObject, "Search Result is 0");
        }else {
            return new jsonResult(jsonObject, "Success");
        }
    }

    @ApiOperation(value="上传单词卡", notes="创建单词卡并提交")
    @PostMapping("/UploadWordCard")
    public jsonResult uploadWordCard (
            @RequestParam("setId") int setId,
            @RequestParam("cardName") String cardName,
            @RequestParam("cardContent") String cardContent,
            @RequestParam(value="audiofile") MultipartFile audiofile,
            @RequestParam(value="picfile") MultipartFile picfile
    ) throws IOException {
        int uploadResult = 0;
        String audio_Url = ossService.uploadFile(audiofile);
        String pic_Url = ossService.uploadFile(picfile);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("audio_Url",audio_Url);
        jsonObject.put("pic_Url",pic_Url);
        Word_card word_card = new Word_card(setId,cardName,cardContent,audio_Url,pic_Url);
        try {
            uploadResult = wordcardService.uploadWordCard(word_card);
        } catch (Exception e){
            uploadResult = 0;
            System.out.println("upload word card error");
        } finally {
        }

        int updateResult = 0;
        try {
            int cardNum = wordcardService.wordCardNum(setId);
            updateResult = wordcardService.updateCardNum(setId,cardNum);
        } catch (Exception e){
            updateResult = 0;
            System.out.println("update word set error");
        } finally {
        }

        if(uploadResult != 0 && updateResult != 0){
            jsonObject.put("isSucceed",true);
            return new jsonResult(jsonObject, "Success");
        }else{
            jsonObject.put("isSucceed",false);
            return new jsonResult(jsonObject, "Upload or Update Error");
        }
    }

    @ApiOperation(value="删除单词卡", notes="根据指定cardId删除单词卡")
    @DeleteMapping("/DeleteWordCard")
    public jsonResult deleteWordSet (
            @RequestParam Integer cardId
    ) {
        int deleteResult=0;
        JSONObject jsonObject=new JSONObject();

        int updateResult = 0;
        int setId = wordcardService.setIdforCardId(cardId);
        try {
            deleteResult = wordcardService.deleteWordCard(cardId);
            //sqlSession.commit();
        } catch (Exception e) {
            System.out.println("delete error");
        } finally {
        }

        try {
            int cardNum = wordcardService.wordCardNum(setId);
            updateResult = wordcardService.updateCardNum(setId,cardNum);
        } catch (Exception e){
            updateResult = 0;
            System.out.println("update word set error");
        } finally {
        }

        if(deleteResult != 0 && updateResult != 0){
            jsonObject.put("isSucceed",true);
            return new jsonResult(jsonObject, "Success");
        }else{
            jsonObject.put("isSucceed",false);
            return new jsonResult(jsonObject, "Delete or Update Error");
        }
    }



}
