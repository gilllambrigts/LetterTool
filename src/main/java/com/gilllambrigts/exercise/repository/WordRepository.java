package com.gilllambrigts.exercise.repository;

import com.gilllambrigts.exercise.model.wordModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface WordRepository extends JpaRepository<wordModel, Long> {
    ArrayList<wordModel> findByEntryId(long entry_id);
}