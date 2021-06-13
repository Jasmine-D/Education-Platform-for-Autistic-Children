package com.example.backend.service;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.dao.userRepository;
import com.example.backend.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userService")
public class userService {
    @Resource
    private userRepository userRepository;
    public List<Object []> verifyLogin(int role,String username, String password){
        List<Object []> temp= userRepository.findVerifyLogin(role,username,password);
        return temp;
    }

    public List<String> checkUsername(String username){
        List<String> temp=userRepository.findCheckUsername(username);
        return temp;
    }
    public List<String> checkPhone(String phone){
        List<String> temp=userRepository.findCheckPhone(phone);
        return temp;
    }
    public void addUser(String username, String password, String phone, int role, Date birthday){
        userRepository.insertUser(username,password,phone,role,birthday);
    }

    public JSONObject findUserById(int user_id){
        List<User> users=userRepository.findUserById(user_id);
        JSONObject jsonObject=new JSONObject();
        if(users.size()==0){
            jsonObject.put("exist",false);
        }else {
            jsonObject.put("exist",true);
            JSONObject obj=new JSONObject();
            obj.put("username",users.get(0).getUsername());
            obj.put("avatar",users.get(0).getAvatar());
            obj.put("phone",users.get(0).getPhone());
            obj.put("password",users.get(0).getPassword());
            obj.put("gender",users.get(0).getGender());
            obj.put("birthday",users.get(0).getBirthday());
            jsonObject.put("user",obj);
        }
        return jsonObject;
    }

    public boolean userExist(int user_id){
        List<User> users=userRepository.findUserById(user_id);
        if(users.size()==0){
            return false;
        }
        else{
            return true;
        }
    }

    public void modifyAvatar(int user_id, String avatar){
        userRepository.modifyAvatar(user_id,avatar);
    }

    public void modifyPassword(int user_id,String password){
        userRepository.modifyPassword(user_id,password);
    }
}
