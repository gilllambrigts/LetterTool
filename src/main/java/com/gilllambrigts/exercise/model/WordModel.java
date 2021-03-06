package com.gilllambrigts.exercise.model;

import javax.persistence.*;

@Entity
@Table(name = "word")
public class WordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "entry_id")
    private long entryId;
    @Column(name = "word")
    private String word;

    public WordModel() {
    }

    public WordModel(long parEntryId, String parWord) {
        this.entryId = parEntryId;
        this.word = parWord;
    }
    public long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }
}