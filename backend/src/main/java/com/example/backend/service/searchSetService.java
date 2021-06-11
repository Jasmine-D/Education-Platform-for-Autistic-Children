package com.example.backend.service;

import com.example.backend.dao.wordSetRepository;
import com.example.backend.domain.Set_collect;
import com.example.backend.domain.Word_set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class searchSetService {

    final
    wordSetRepository wordsetRepository;

    @Autowired
    public searchSetService(wordSetRepository wordsetRepository) {
        this.wordsetRepository = wordsetRepository;
    }

    public List<Word_set> searchResult(String name, int pageNum, int curPage){
        return wordsetRepository.searchResult(name,pageNum,curPage);
    }
    public int searchResultNum(String name) {
        return this.wordsetRepository.searchResultNum(name);
    }

    public List<Word_set> getAllSet(int pageNum, int curPage){
        return wordsetRepository.getAllSet(pageNum,curPage);
    }
    public int allSetNum(){
        return wordsetRepository.allSetNum();
    }


    public int isStar(int userId, int setId){
        return wordsetRepository.isStar(userId, setId);
    }

    public int isKeyMatch(int setId, String pwd){
        return wordsetRepository.isKeyMatch(setId, pwd);
    }

    public int starWordSet(Set_collect set_collect){
        return wordsetRepository.starWordSet(set_collect);
    }

    public int notStarWordSet(int userId, int setId){
        return wordsetRepository.notStarWordSet(userId, setId);
    }
}
