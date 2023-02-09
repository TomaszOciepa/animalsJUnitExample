package com.tom.animalsy.Repository;

import com.tom.animalsy.model.Animals;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalsRepo extends MongoRepository<Animals, String>{

     List<Animals> findByOwnerId(String ownerId);

}
