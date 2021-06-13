package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.controller.json.jsonResult;
import com.example.backend.domain.Report;
import com.example.backend.service.reportService;
import com.example.backend.service.sceneService;
import com.example.backend.service.userService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping(value="/Report",produces = "application/json;charset=UTF-8")
@CrossOrigin
public class reportController {
    @Resource
    private reportService reportService;
    @Resource
    private sceneService sceneService;
    @Resource
    private userService userService;
    @GetMapping(value = "/getAllReport")
    @ApiOperation(value = "获取某个用户所有学习报告", notes = "根据用户id查找某个用户所有学习报告")
    public jsonResult getAllReport(@RequestParam("user_id") int user_id){
        JSONObject jsonObject=reportService.getAllReport(user_id);
        return new jsonResult<>(jsonObject,"获取学习报告成功");
    }

    @GetMapping(value="/getSomeReport",produces = "application/json;charset=UTF-8")
    @ApiOperation(value="获取某个用户在某个场景的学习报告",notes="根据用户id和场景id查找学习报告")
    public jsonResult getSomeReport(int user_id,int scene_id){
        JSONObject jsonObject=reportService.findSomeReport(user_id,scene_id);
        return new jsonResult<>(jsonObject,"已存在学习报告！");
    }

    @PostMapping(value="/createReport")
    @ApiOperation(value="用户创建学习报告",notes = "用户在某个场景创建学习报告")
    public jsonResult createReport(
            @RequestParam("user_id") int user_id,
            @RequestParam("scene_id") int scene_id,
            @RequestParam("score") int score,
            @RequestParam("comment") String comment,
            @RequestParam("teacher") String teacher
    ){
        if(userService.userExist(user_id)){
            if(sceneService.sceneExist(scene_id)){
                Report report=new Report();
                reportService.insertReport(user_id,scene_id,report.getTime(),score,comment,teacher);
                return  new jsonResult<>(true,"创建学习报告成功");
            }
            else{
                return  new jsonResult<>(false,"场景不存在，创建学习报告失败！");
            }
        }
        else {
            return  new jsonResult<>(false,"用户不存在，创建学习报告失败！");
        }

    }

    @PostMapping(value="/modifyReport")
    @ApiOperation(value="用户更新学习报告",notes = "用户在某个场景更新学习报告")
    public jsonResult modifyReport(
            @RequestParam("report_id") int report_id,
            @RequestParam("score") int score,
            @RequestParam("comment") String comment,
            @RequestParam("teacher") String teacher
    ){
        Report report=new Report();
        reportService.updateReport(report_id,report.getTime(),score,comment,teacher);
        return  new jsonResult<>(true,"更新学习报告成功!");
    }
}
