package com.example.backend.service;

import com.example.backend.dao.wordCardRepository;
import com.example.backend.dao.wordSetRepository;
import com.example.backend.domain.Word_card;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class wordCardService{
    final
    wordCardRepository wordcardRepository;

    public wordCardService(wordCardRepository wordcardRepository) {
        this.wordcardRepository = wordcardRepository;
    }

    public List<Word_card> wordCard(int setId, int pageNum, int curPage){
        return wordcardRepository.wordCard(setId, pageNum, curPage);
    }

    public int wordCardNum(int setId){
        return wordcardRepository.wordCardNum(setId);
    }

    public int uploadWordCard(Word_card word_card){
        return wordcardRepository.uploadWordCard(word_card);
    }

    public int deleteWordCard(int cardId){
        return wordcardRepository.deleteWordCard(cardId);
    }

    public int updateCardNum(int setId, int cardNum){
        return wordcardRepository.updateCardNum(setId,cardNum);
    }

    public int setIdforCardId(int cardId){
        return wordcardRepository.setIdforCardId(cardId);
    }


}

