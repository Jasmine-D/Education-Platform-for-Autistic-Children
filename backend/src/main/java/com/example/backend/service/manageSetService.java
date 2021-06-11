package com.example.backend.service;

import com.example.backend.dao.wordSetRepository;
import com.example.backend.domain.Set_collect;
import com.example.backend.domain.Word_set;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class manageSetService {

    final
    wordSetRepository wordsetRepository;

    public manageSetService(wordSetRepository wordsetRepository) {
        this.wordsetRepository = wordsetRepository;
    }

    public String userName(int userId){
        return wordsetRepository.userName(userId);
    }

    public int uploadWordSet(Word_set word_set){
        return wordsetRepository.uploadWordSet(word_set);
    }

    public int deleteWordSet(int setId){
        return wordsetRepository.deleteWordSet(setId);
    }

    public List<Word_set> myStarWordSet(int userId, int pageNum, int curPage){
        return wordsetRepository.myStarWordSet(userId, pageNum, curPage);
    };
    public int myStarWordSetNum(int userId){
        return wordsetRepository.myStarWordSetNum(userId);
    }

    public List<Word_set> myWordSet(String userName, int pageNum, int curPage){
        return wordsetRepository.myWordSet(userName, pageNum, curPage);
    };
    public int myWordSetNum(String userName){
        return wordsetRepository.myWordSetNum(userName);
    }


}
