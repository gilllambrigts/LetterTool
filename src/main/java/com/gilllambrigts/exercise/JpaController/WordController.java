package com.gilllambrigts.exercise.JpaController;

import com.gilllambrigts.exercise.model.wordModel;
import com.gilllambrigts.exercise.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class WordController {
    @Autowired
    WordRepository repo;

    public void save(wordModel model){
        repo.save(model);
    }

    public void saveAll(ArrayList<wordModel> wordModelList){
        for(int i = 0; i < wordModelList.size() - 1; i++){
            save(wordModelList.get(i));
        }
    }

    public ArrayList<wordModel> getAllForEntryId(long entryId){
        return repo.findByEntryId(entryId);
    }
}
