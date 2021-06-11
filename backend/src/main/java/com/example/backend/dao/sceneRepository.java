package com.example.backend.dao;

import com.example.backend.domain.Scene;
import com.example.backend.domain.ScenePic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface sceneRepository extends JpaRepository<Scene, Integer> {
    @Query(value="select * from scene where is_delete=0",nativeQuery = true)
    List<Scene> getAllScene();
    @Query(value="select * from scene where is_delete=0 AND scene_name like concat('%',?1,'%')",nativeQuery = true)
    List<Scene> findSceneByName(String key);
    @Query(value="select * from scene where is_delete=0 AND creator=?1",nativeQuery = true)
    List<Scene> findCreatedScene(String creator);

    @Query(value = "select is_collect from scene_collect where user_id=?1 AND scene_id=?2",nativeQuery = true)
    List<Integer> querySceneCollect(int user_id,int scene_id);
    @Modifying
    @Transactional
    @Query(value="insert into scene_collect(user_id,scene_id,is_collect) values(?1,?2,?3)",nativeQuery = true)
    void insertSceneCollect(int user_id, int scene_id, int is_collect);
    @Modifying
    @Transactional
    @Query(value="update scene_collect set is_collect=?3 where user_id=?1 AND scene_id=?2" ,nativeQuery = true)
    void modifySceneCollect(int user_id, int scene_id, int is_collect);

    @Modifying
    @Transactional
    @Query(value="update scene set is_visible=0,pwd=?2 where scene_id=?1" ,nativeQuery = true)
    void setPassword(int scene_id, String pwd);
    @Modifying
    @Transactional
    @Query(value="update scene set is_visible=1 where scene_id=?1" ,nativeQuery = true)
    void setPublic(int scene_id);

    @Modifying
    @Transactional
    @Query(value="update scene set is_delete=1 where scene_id=?1" ,nativeQuery = true)
    void deleteScene(int scene_id);

    @Modifying
    @Transactional
    @Query(value="update scene_collect set is_collect=0 where scene_id=?1" ,nativeQuery = true)
    void cancelCollect(int scene_id);

    @Query(value="select * from scene where scene_id=?1",nativeQuery = true)
    Scene querySceneById(int scene_id);

    @Query(value = "select max(scene_id) from scene",nativeQuery = true)
    Integer returnMaxSceneId();

    @Modifying
    @Transactional
    @Query(value="insert into scene(scene_name,creator,is_visible,pwd,tags,pic_url,is_video,video_url) values(?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
    void insertScene(String scene_name, String creator, int is_visible,String pwd,String tags,String pic_url,int is_video,String video_url);//插入新的场景数据


}
