package com.gilllambrigts.exercise.repository;

import com.gilllambrigts.exercise.model.WordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;

public interface WordRepository extends JpaRepository<WordModel, Long> {
    ArrayList<WordModel> findByEntryId(long entry_id);
}