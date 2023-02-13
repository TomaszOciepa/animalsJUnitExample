package com.tom.animalsy.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Animals {

    @Id
    private String id;
    private String name;
    private String kindOfanimmal;
    private int age;
    private Gender gender;

    private String ownerId;

    public Animals(String name, String kindOfanimmal, int age, Gender gender, String ownerId) {
        this.name = name;
        this.kindOfanimmal = kindOfanimmal;
        this.age = age;
        this.gender = gender;
        this.ownerId = ownerId;
    }



    public Animals(String id, String name, String kindOfanimmal, int age, Gender gender, String ownerId) {
        this.id = id;
        this.name = name;
        this.kindOfanimmal = kindOfanimmal;
        this.age = age;
        this.gender = gender;
        this.ownerId = ownerId;
    }
}
