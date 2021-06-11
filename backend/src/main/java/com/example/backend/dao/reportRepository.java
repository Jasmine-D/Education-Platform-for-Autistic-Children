package com.example.backend.dao;

import com.example.backend.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface reportRepository extends JpaRepository<Report, Integer> {
    @Query(value = "select * from report where user_id=?1 AND scene_id=?2",nativeQuery = true)
    List<Report> querySomeReport(int user_id, int scene_id);
    @Query(value = "select * from report where user_id=?1",nativeQuery = true)
    List<Report> queryReport(int user_id);

    @Modifying
    @Transactional
    @Query(value="insert into report(user_id,scene_id,time,score,comment,teacher) values(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    void insertReport(int user_id, int scene_id, Date time,int score,String comment,String teacher);

    @Modifying
    @Transactional
    @Query(value="update report set time=?2,score=?3,comment=?4,teacher=?5 where report_id=?1" ,nativeQuery = true)
    void modifyReport(int report_id, Date time,int score,String comment,String teacher);
}
