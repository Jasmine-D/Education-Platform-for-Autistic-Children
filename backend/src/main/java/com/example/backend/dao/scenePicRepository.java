package com.example.backend.dao;

import com.example.backend.domain.Scene;
import com.example.backend.domain.ScenePic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface scenePicRepository extends JpaRepository<ScenePic, Integer> {
    @Query(value="select * from scene_pic where scene_id=?1",nativeQuery = true)
    List<ScenePic> queryPicById(int scene_id);

    @Modifying
    @Transactional
    @Query(value="insert into scene_pic(scene_id,pic_url,keyword,description) values(?1,?2,?3,?4)",nativeQuery = true)
    void insertScenePic(int scene_id, String pic_url, String keyword,String description);//插入新的场景数据
}
