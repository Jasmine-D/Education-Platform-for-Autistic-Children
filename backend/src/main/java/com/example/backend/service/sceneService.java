package com.example.backend.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.dao.scenePicRepository;
import com.example.backend.dao.sceneQuestionRepository;
import com.example.backend.dao.sceneRepository;
import com.example.backend.domain.Scene;
import com.example.backend.domain.ScenePic;
import com.example.backend.domain.SceneQuestion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("sceneService")
public class sceneService {
    @Resource
    private sceneRepository sceneRepository;

    @Resource
    private scenePicRepository scenePicRepository;

    @Resource
    private sceneQuestionRepository sceneQuestionRepository;



    public JSONObject getAllScene(int currentPage, int pageSize,int user_id){//获取所有场景
        List<Scene> scenes=sceneRepository.getAllScene();
        return getScene(currentPage,pageSize,user_id,scenes);
    }
    public JSONObject getSceneByName(int currentPage, int pageSize,int user_id,String key){//搜索场景
        List<Scene> scenes=sceneRepository.findSceneByName(key);
        return getScene(currentPage,pageSize,user_id,scenes);
    }
    public JSONObject getCreatedScene(int currentPage, int pageSize,int user_id,String creator){
        List<Scene> scenes=sceneRepository.findCreatedScene(creator);
        return getScene(currentPage,pageSize,user_id,scenes);
    }
    public JSONObject getCollectScene(int currentPage, int pageSize,int user_id){//获取所有被用户收藏的场景
        List<Scene> scenes=sceneRepository.getAllScene();
        return getUserCollectScene(currentPage,pageSize,user_id,scenes);
    }

    private JSONObject getScene(int currentPage, int pageSize, int user_id, List<Scene> scenes){//获取分页场景列表
        JSONArray jsonArray=new JSONArray();
        if(scenes.isEmpty()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("total",0);
            //jsonArray.add(jsonObject);
            return jsonObject;
        }
        if(currentPage<1||pageSize<1){//如果参数值不合理
            return new JSONObject();
        }
        int total=scenes.size();
        int start=0;
        int end=0;
        if(total>(currentPage-1)*pageSize){
            start=(currentPage-1)*pageSize;
            if(total>currentPage*pageSize){
                end=currentPage*pageSize;
            }else{
                end=total;
            }
        }else{
            return new JSONObject();
        }
        for(int i=start;i<end;i++){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("scene_id", scenes.get(i).getScene_id());
            jsonObject.put("creator",scenes.get(i).getCreator());
            jsonObject.put("scene_name",scenes.get(i).getScene_name());
            jsonObject.put("tags",scenes.get(i).getTags());
            if(scenes.get(i).getIs_visible()==1){
                jsonObject.put("is_visible",true);
            }else{
                jsonObject.put("is_visible",false);
            }
            jsonObject.put("pwd",scenes.get(i).getPwd());
            if(scenes.get(i).getIs_video()==1){
                jsonObject.put("is_video",true);
            }else{
                jsonObject.put("is_video",false);
            }
            jsonObject.put("video_url",scenes.get(i).getVideo_url());
            jsonObject.put("pic_url",scenes.get(i).getPic_url());
            List<Integer> collects=sceneRepository.querySceneCollect(user_id,scenes.get(i).getScene_id());
            if(collects.size()==0){
                jsonObject.put("is_collect",false);
                sceneRepository.insertSceneCollect(user_id,scenes.get(i).getScene_id(),0);
            }else{
                if(collects.get(0)==0){
                    jsonObject.put("is_collect",false);
                }else{
                    jsonObject.put("is_collect",true);
                }
            }
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("scenes",jsonArray);
        jsonObject.put("total",total);
        return jsonObject;
    }

    private JSONObject getUserCollectScene(int currentPage, int pageSize, int user_id, List<Scene> scenes){
        for(int i=0;i<scenes.size();i++){
            List<Integer> collects=sceneRepository.querySceneCollect(user_id,scenes.get(i).getScene_id());
            if(collects.size()==0){
                sceneRepository.insertSceneCollect(user_id,scenes.get(i).getScene_id(),0);
                scenes.remove(i);
                i--;
            }else{
                if(collects.get(0)==0){
                    scenes.remove(i);
                    i--;
                }
            }
        }
        if(scenes.isEmpty()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("total",0);
            return jsonObject;
        }
        if(currentPage<1||pageSize<1){//如果参数值不合理
            return new JSONObject();
        }
        int total=scenes.size();
        int start=0;
        int end=0;
        if(total>(currentPage-1)*pageSize){
            start=(currentPage-1)*pageSize;
            if(total>currentPage*pageSize){
                end=currentPage*pageSize;
            }else{
                end=total;
            }
        }else{
            return new JSONObject();
        }
        JSONArray jsonArray=new JSONArray();
        for(int i=start;i<end;i++){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("scene_id", scenes.get(i).getScene_id());
            jsonObject.put("creator",scenes.get(i).getCreator());
            jsonObject.put("scene_name",scenes.get(i).getScene_name());
            jsonObject.put("tags",scenes.get(i).getTags());
            if(scenes.get(i).getIs_visible()==1){
                jsonObject.put("is_visible",true);
            }else{
                jsonObject.put("is_visible",false);
            }
            jsonObject.put("pwd",scenes.get(i).getPwd());
            if(scenes.get(i).getIs_video()==1){
                jsonObject.put("is_video",true);
            }else{
                jsonObject.put("is_video",false);
            }
            jsonObject.put("video_url",scenes.get(i).getVideo_url());
            jsonObject.put("pic_url",scenes.get(i).getPic_url());
            jsonObject.put("is_collect",true);
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("scenes",jsonArray);
        jsonObject.put("total",total);
        return jsonObject;
    }
    public int collectOrCancel(int user_id,int scene_id){//收藏或取消收藏
        List<Integer> collects=sceneRepository.querySceneCollect(user_id,scene_id);
        if(collects.size()==0){
            sceneRepository.insertSceneCollect(user_id,scene_id,1);
            return 1;
        }else{
            if(collects.get(0)==0){//未收藏
                sceneRepository.modifySceneCollect(user_id,scene_id,1);//收藏
                return 1;
            }else{//已收藏
                sceneRepository.modifySceneCollect(user_id,scene_id,0);//取消收藏
                return 0;
            }
        }
    }

    public void setPassword(int scene_id,String pwd){
        sceneRepository.setPassword(scene_id,pwd);//设置某个场景密码
        sceneRepository.cancelCollect(scene_id);//将此场景移出其它用户收藏夹
    }

    public void setPublic(int scene_id){//公开某个场景
        sceneRepository.setPublic(scene_id);
    }

    public void deleteScene(int scene_id){//删除某个场景
        sceneRepository.deleteScene(scene_id);
    }

    public JSONObject getScenePic(int scene_id){//获取场景学习环节图片
        List<ScenePic> scenePics=scenePicRepository.queryPicById(scene_id);
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        int total=scenePics.size();
        jsonObject.put("total",total);
        for (ScenePic scenePic : scenePics) {
            JSONObject obj = new JSONObject();
            obj.put("pic_id", scenePic.getPic_id());
            obj.put("pic_url", scenePic.getPic_url());
            obj.put("keyword", scenePic.getKeyword());
            obj.put("description", scenePic.getDescription());
            jsonArray.add(obj);
        }
        jsonObject.put("scenePic",jsonArray);
        return jsonObject;
    }

    public JSONObject getSceneInfo(int user_id,int scene_id){//获取场景信息
        JSONObject jsonObject=new JSONObject();
        Scene scene=sceneRepository.querySceneById(scene_id);
        jsonObject.put("scene_name",scene.getScene_name());
        jsonObject.put("tags",scene.getTags());
        if(scene.getIs_video()==1){
            jsonObject.put("is_video",true);
        }else{
            jsonObject.put("is_video",false);
        }
        jsonObject.put("video_url",scene.getVideo_url());
        List<Integer> collects=sceneRepository.querySceneCollect(user_id,scene.getScene_id());
        if(collects.size()==0){
            jsonObject.put("is_collect",false);
            sceneRepository.insertSceneCollect(user_id,scene.getScene_id(),0);
        }else{
            if(collects.get(0)==0){
                jsonObject.put("is_collect",false);
            }else{
                jsonObject.put("is_collect",true);
            }
        }
        return jsonObject;
    }

    public JSONObject getSceneQuestion(int scene_id){//获取场景问题列表
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        List<SceneQuestion> sceneQuestions=sceneQuestionRepository.queryQuestionById(scene_id);
        int total=sceneQuestions.size();
        jsonObject.put("total",total);
        for (SceneQuestion sceneQuestion : sceneQuestions) {
            JSONObject obj = new JSONObject();
            obj.put("question_id", sceneQuestion.getQuestion_id());
            obj.put("content", sceneQuestion.getContent());
            obj.put("pic_url", sceneQuestion.getPic_url());
            jsonArray.add(obj);
        }
        jsonObject.put("question",jsonArray);
        return jsonObject;
    }

    public int insertScene(String scene_name,String creator,int is_visible,String pwd,String tags,String pic_url,int is_video,String video_url){//插入场景
        sceneRepository.insertScene(scene_name,creator,is_visible,pwd,tags,pic_url,is_video,video_url);
        return sceneRepository.returnMaxSceneId();
    }

    public void insertScenePic(int scene_id,String pic_url,String keyword,String description){//插入场景图片及描述
        scenePicRepository.insertScenePic(scene_id,pic_url,keyword,description);
    }

    public void insertQuestion(int scene_id,String content,String pic_url){//插入场景问题
        sceneQuestionRepository.insertQuestion(scene_id,content,pic_url);
    }
}
