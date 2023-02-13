package com.tom.animalsy.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
public class Owner {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    //    @Indexed(unique = true)
    private String email;
    private Address address;
    private LocalDateTime created;
    private List<String> animalsList;

    public Owner(String firstName, String lastName, String email, Address address, LocalDateTime created, List<String> animalsList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.created = created;
        this.animalsList = animalsList;
    }

    public Owner(String id, String firstName, String lastName, String email, Address address, LocalDateTime created, List<String> animalsList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.created = created;
        this.animalsList = animalsList;
    }
}
