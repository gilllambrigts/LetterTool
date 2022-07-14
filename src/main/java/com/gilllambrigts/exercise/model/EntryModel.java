package com.gilllambrigts.exercise.model;

import javax.persistence.*;

@Entity
@Table(name = "entry")
public class EntryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "submission_date")
    private String submission_date;

    public EntryModel() {
    }
    public EntryModel(String date) {
        this.submission_date = date;
    }
    public long getId() {
        return id;
    }
}