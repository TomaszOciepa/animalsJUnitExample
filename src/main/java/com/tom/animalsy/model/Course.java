package com.tom.animalsy.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Course {

    @Id
    private String id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> animalLists;

    public Course(String name, LocalDateTime startDate, LocalDateTime endDate, List<String> animalLists) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.animalLists = animalLists;
    }
}
