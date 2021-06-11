package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.controller.json.jsonResult;
import com.example.backend.domain.User;
import com.example.backend.service.ossService;
import com.example.backend.service.tokenService;
import com.example.backend.service.userService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/User",produces = "application/json;charset=UTF-8")
@CrossOrigin
public class userController {
    @Resource
    private userService userService;

    @Resource
    private tokenService tokenService;

    @Resource
    private ossService ossService;

    @GetMapping(value = "/Login")
    @CrossOrigin
    @ApiOperation(value = "登陆", notes = "选择角色，输入用户名，密码登陆")
    public jsonResult login(
            @RequestParam("role") int role,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){

        List<Object []> temp=userService.verifyLogin(role,username, password);
        if(temp.size()==0)
        {
            return new jsonResult<>(temp, "输入的用户名或者密码有误");
        }
        else
        {
            JSONObject jsonObject=new JSONObject();

            String []fields={"user_id","role","username","avatar"};
            Object []objects=temp.get(0);
            for(int i=0;i<objects.length;i++)
            {
                jsonObject.put(fields[i],objects[i]);
            }
            String token = tokenService.getToken(objects[0].toString(),password);
            jsonObject.put("token",token);
            return new jsonResult<>(jsonObject, "登陆成功");
        }
    }

    @GetMapping(value = "/CheckName")
    @CrossOrigin
    @ApiOperation(value = "检查注册昵称是否重复", notes = "输入注册昵称")
    public jsonResult checkUserName(@RequestParam("username") String username){
        List<String> temp=userService.checkUsername(username);
        if(temp.size()==0){
            return new jsonResult<>(true,"昵称没有重复");
        }else{
            return new jsonResult<>(false,"昵称重复");
        }
    }

    @GetMapping(value = "/CheckPhone")
    @CrossOrigin
    @ApiOperation(value = "检查注册手机号是否重复",notes="输入注册手机号")
    public jsonResult checkPhone(@RequestParam("phone") String phone){
        List<String> temp=userService.checkPhone(phone);
        if(temp.size()==0){
            return new jsonResult<>(true,"该手机号没有被占用");
        }else{
            return new jsonResult<>(false,"该手机号已被占用");
        }
    }

    @GetMapping(value = "/Register")
    @CrossOrigin
    @ApiOperation(value = "注册", notes = "选择注册角色，输入昵称，手机号，密码注册")
    public jsonResult register(@RequestParam("role") int role,
                               @RequestParam("phone") String phone,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password){
        User user=new User();
        userService.addUser(username,password,phone,role,user.getBirthday());
        return new jsonResult<>(true,"注册成功");
    }

    @GetMapping(value = "/GetUserInfo")
    @CrossOrigin
    @ApiOperation(value = "获得用户账户信息", notes = "根据用户id查找到对应用户")
    public jsonResult getUserInfo(@RequestParam("user_id") int user_id){
        JSONObject jsonObject=userService.findUserById(user_id);
        return new jsonResult<>(jsonObject);
    }

    @PostMapping(value = "/ModifyAvatar",headers="content-type=multipart/form-data")
    @ApiOperation(value = "修改用户头像", notes = "上传用户头像")
    public jsonResult modifyAvatar(
            @RequestParam(value="user_id") int user_id,
            @RequestParam(value="file") MultipartFile file
    ) throws IOException{
        System.out.println("[文件类型] - [{}]"+ file.getContentType());
        System.out.println("[文件名称] - [{}]"+ file.getOriginalFilename());
        System.out.println("[文件大小] - [{}]"+ file.getSize());
        String url=ossService.uploadFile(file);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("user_id",user_id);
        jsonObject.put("avatar",url);
        userService.modifyAvatar(user_id,url);
        return new jsonResult<>(jsonObject,"修改头像成功");
    }

    @PostMapping(value = "/ModifyPassword")
    @ApiOperation(value = "修改用户密码", notes = "用户修改密码")
    public jsonResult modifyPassword(
            @RequestParam(value="user_id") int user_id,
            @RequestParam(value="password") String password
    ){
        userService.modifyPassword(user_id,password);
        return new jsonResult<>(true,"修改密码成功");
    }

}
