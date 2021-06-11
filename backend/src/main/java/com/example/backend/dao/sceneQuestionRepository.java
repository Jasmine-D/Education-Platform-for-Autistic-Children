package com.example.backend.dao;

import com.example.backend.domain.SceneQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface sceneQuestionRepository extends JpaRepository<SceneQuestion, Integer> {
    @Query(value="select * from question where scene_id=?1",nativeQuery = true)
    List<SceneQuestion> queryQuestionById(int scene_id);

    @Modifying
    @Transactional
    @Query(value="insert into question(scene_id,content,pic_url) values(?1,?2,?3)",nativeQuery = true)
    void insertQuestion(int scene_id, String content, String pic_url);//插入新的问题数据
}
