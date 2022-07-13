package com.gilllambrigts.exercise.JpaController;

import com.gilllambrigts.exercise.model.EntryModel;
import com.gilllambrigts.exercise.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntryController {
    @Autowired
    EntryRepository repo;

    public void save(EntryModel entryModel){
        repo.saveAndFlush(entryModel);
    }

    public void getId(){
    }
}
