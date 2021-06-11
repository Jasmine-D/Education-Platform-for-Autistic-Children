package com.example.backend.dao;

import com.example.backend.domain.Set_collect;
import com.example.backend.domain.Word_set;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface wordSetRepository {
    //返回按名搜索的word_set信息
    List<Word_set> searchResult(String name, int pageNum, int curPage);
    int searchResultNum(String name);

    //返回所有的word_set信息
    List<Word_set> getAllSet(int pageNum, int curPage);
    int allSetNum();

    //返回某个单词集是否被用户收藏
    int isStar(int userId, int setId);

    //返回某个单词集的密码是否匹配
    int isKeyMatch(int setId, String pwd);

    //返回username填充单词集新建需要的set_creator
    String userName(int userId);

    //返回单词集新建是否成功
    int uploadWordSet(Word_set word_set);

    //删除指定单词集，返回是否成功
    int deleteWordSet(int setId);

    //收藏单词集，返回是否收藏成功
    int starWordSet(Set_collect set_collect);

    //取消收藏单词集，返回是否取消收藏成功
    int notStarWordSet(int userId, int setId);

    //查看我的收藏
    List<Word_set> myStarWordSet(int userId, int pageNum, int curPage);
    int myStarWordSetNum(int userId);

    //查看我自己制作的单词集
    List<Word_set> myWordSet(String userName, int pageNum, int curPage);
    int myWordSetNum(String userName);

}


