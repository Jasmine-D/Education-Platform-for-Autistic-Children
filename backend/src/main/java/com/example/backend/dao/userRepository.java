package com.example.backend.dao;

import com.example.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface userRepository extends JpaRepository<User, Integer> {
    @Query(value="select user_id,role,username,avatar from user where role=?1 AND username=?2 AND password=?3",nativeQuery = true)
    List<Object []> findVerifyLogin(int role,String username, String password);

    @Query(value="select * from user where user_id=?1",nativeQuery = true)
    List<User> findUserById(int user_id);
    @Query(value="select username from user where username=?1",nativeQuery = true)
    List<String>findCheckUsername(String username);
    @Query(value="select phone from user where  phone=?1",nativeQuery = true)
    List<String>findCheckPhone(String phone);
    @Modifying
    @Transactional
    @Query(value="insert into user(username,password,phone,role,birthday) values(?1,?2,?3,?4,?5)",nativeQuery = true)
    void insertUser(String username, String password, String phone, int role, Date birthday);

    @Modifying
    @Transactional
    @Query(value="update user set avatar=?2 where user_id=?1" ,nativeQuery = true)
    void modifyAvatar(int user_id, String avatar);

    @Modifying
    @Transactional
    @Query(value="update user set password=?2 where user_id=?1" ,nativeQuery = true)
    void modifyPassword(int user_id, String password);
}
