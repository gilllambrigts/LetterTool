package com.gilllambrigts.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gilllambrigts.exercise.model.EntryModel;

public interface EntryRepository extends JpaRepository<EntryModel, Long> {
}
