package com.example.backend.dao;

import com.example.backend.domain.Word_card;
import com.example.backend.domain.Word_set;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface wordCardRepository {
    ///返回按set_id搜索的word_card信息
    List<Word_card> wordCard(int setId, int pageNum, int curPage);
    int wordCardNum(int setId);

    //返回单词集新建是否成功
    int uploadWordCard(Word_card word_card);

    //删除指定单词集，返回是否成功
    int deleteWordCard(int cardId);

    //更新word_set表里的card_num参数
    int updateCardNum(int setId, int cardNum);

    //返回指定card_id的set_id
    int setIdforCardId(int cardId);
}
