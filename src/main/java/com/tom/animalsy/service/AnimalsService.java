package com.tom.animalsy.service;

import com.tom.animalsy.model.Animals;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AnimalsService {

    List<Animals> getAnimals();

    Animals getAnimal(String id);

    Animals addAnimal(Animals animal);

    void deleteAnimal(String id);

    Animals putAnimal(String id, Animals animal);

    List<Animals> findAnimalsByOwnerId(String ownerId);

}
