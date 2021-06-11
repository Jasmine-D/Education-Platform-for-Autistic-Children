package com.example.backend.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.dao.reportRepository;
import com.example.backend.dao.sceneRepository;
import com.example.backend.domain.Report;
import com.example.backend.domain.Scene;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("reportService")
public class reportService {
    @Resource
    private reportRepository reportRepository;

    @Resource
    private sceneRepository sceneRepository;
    public JSONObject getAllReport(int user_id){//获取某个用户所有学习报告
        List<Report>reports=reportRepository.queryReport(user_id);
        JSONArray jsonArray=new JSONArray();
        int total=reports.size();
        for (Report report : reports) {
            JSONObject jsonObject = new JSONObject();
            Scene scene = sceneRepository.querySceneById(report.getScene_id());
            jsonObject.put("scene_id", report.getScene_id());
            jsonObject.put("report_id", report.getReport_id());
            jsonObject.put("time", report.getTime());
            jsonObject.put("score", report.getScore());
            jsonObject.put("comment", report.getComment());
            jsonObject.put("teacher", report.getTeacher());
            jsonObject.put("scene_name", scene.getScene_name());
            jsonObject.put("tags", scene.getTags());
            jsonObject.put("creator", scene.getCreator());
            jsonObject.put("operation", "查看报告");
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("total",total);
        jsonObject.put("reports",jsonArray);
        return jsonObject;
    }

    public JSONObject findSomeReport(int user_id,int scene_id){
        List<Report>reports=reportRepository.querySomeReport(user_id,scene_id);
        JSONObject jsonObject=new JSONObject();
        if(reports.size()==0){//该用户还没有该场景的学习报告
            jsonObject.put("exist",false);
            return jsonObject;
        }
        else{
            jsonObject.put("exist",true);
            JSONObject obj=new JSONObject();
            obj.put("report_id",reports.get(0).getReport_id());
            obj.put("time",reports.get(0).getTime());
            obj.put("score",reports.get(0).getScore());
            obj.put("comment",reports.get(0).getComment());
            obj.put("teacher",reports.get(0).getTeacher());
            jsonObject.put("report",obj);
        }
        return jsonObject;
    }

    public void insertReport(int user_id, int scene_id, Date time,int score,String comment,String teacher){
        reportRepository.insertReport(user_id,scene_id,time,score,comment,teacher);
    }

    public void updateReport(int report_id,Date time,int score,String comment,String teacher){
        reportRepository.modifyReport(report_id,time,score,comment,teacher);
    }

}
