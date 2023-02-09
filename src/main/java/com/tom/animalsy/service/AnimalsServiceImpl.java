package com.tom.animalsy.service;

import com.tom.animalsy.Repository.AnimalsRepo;
import com.tom.animalsy.model.Animals;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalsServiceImpl implements AnimalsService {

    private final AnimalsRepo animalsRepo;

    public AnimalsServiceImpl(AnimalsRepo animalsRepo) {
        this.animalsRepo = animalsRepo;
    }

    @Override
    public List<Animals> getAnimals() {
        return animalsRepo.findAll();
    }

    @Override
    public Animals getAnimal(String id) {
        return animalsRepo.findById(id).get();
    }

    @Override
    public Animals addAnimal(Animals animal) {
        return animalsRepo.insert(animal);
    }

    @Override
    public void deleteAnimal(String id) {
        animalsRepo.deleteById(id);
    }

    @Override
    public Animals putAnimal(String id, Animals animal) {
        Optional<Animals> animalsOptional = animalsRepo.findById(id);
        if (animalsOptional.isPresent()) {
            animalsOptional.get().setName(animal.getName());
            animalsOptional.get().setAge(animal.getAge());
            animalsOptional.get().setGender(animal.getGender());
            animalsOptional.get().setOwnerId(animal.getOwnerId());
            return animalsRepo.save(animalsOptional.get());
        } else {
            return animalsOptional.get();
        }

    }

    @Override
    public List<Animals> findAnimalsByOwnerId(String ownerId){
        return animalsRepo.findByOwnerId(ownerId);
    }
}
