package com.gilllambrigts.exercise.JpaController;

import com.gilllambrigts.exercise.model.WordModel;
import com.gilllambrigts.exercise.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class WordController {
    @Autowired
    WordRepository repo;

    public void save(WordModel model){
        repo.save(model);
    }

    public void saveAll(ArrayList<WordModel> wordModelList){
        for (WordModel wordModel : wordModelList) {
            System.out.println(wordModel);
            save(wordModel);
        }
    }

    public ArrayList<WordModel> getAllForEntryId(long entryId){
        return repo.findByEntryId(entryId);
    }
}
