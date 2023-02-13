package com.tom.animalsy.service;

import com.tom.animalsy.Repository.AnimalsRepo;
import com.tom.animalsy.Repository.OwnerRepository;
import com.tom.animalsy.model.Animals;
import com.tom.animalsy.model.Owner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final AnimalsRepo animalsRepo;

    public OwnerServiceImpl(OwnerRepository ownerRepository, AnimalsRepo animalsRepo) {
        this.ownerRepository = ownerRepository;
        this.animalsRepo = animalsRepo;
    }


    @Override
    public List<Owner> getOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner getOwnerById(String id) {
        return ownerRepository.findById(id).get();
    }

    @Override
    public Owner addOwner(Owner owner) {
        List<String> list = new ArrayList<>();
        owner.setAnimalsList(list);
        return ownerRepository.save(owner);
    }

    @Override
    public void deleteOwner(String id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public Owner putOwner(String id, Owner owner) {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);

        if (optionalOwner.isPresent()) {
            optionalOwner.get().setFirstName(owner.getFirstName());
            optionalOwner.get().setLastName(owner.getLastName());
            optionalOwner.get().setEmail(owner.getEmail());
            optionalOwner.get().setAddress(owner.getAddress());
            optionalOwner.get().setAnimalsList(owner.getAnimalsList());
            return ownerRepository.save(optionalOwner.get());
        } else {
            return optionalOwner.get();
        }
    }

    @Override
    public Owner addAnimal(String ownerId, String animalId) {
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);

        if (optionalOwner.isPresent()) {
            saveAnimalInOwner(animalId, optionalOwner);
            saveOwnerInAnimal(ownerId, animalId);
            return ownerRepository.save(optionalOwner.get());
        } else {
            return optionalOwner.get();
        }
    }

    private void saveAnimalInOwner(String animalId, Optional<Owner> optionalOwner) {
        List<String> animalsList = optionalOwner.get().getAnimalsList();
        animalsList.add(animalId);
        optionalOwner.get().setAnimalsList(animalsList);
    }

    private void saveOwnerInAnimal(String ownerId, String animalId) {
        Optional<Animals> optionalAnimal = animalsRepo.findById(animalId);
        optionalAnimal.get().setOwnerId(ownerId);
        animalsRepo.save(optionalAnimal.get());
    }

    @Override
    public Owner deleteAnimal(String ownerId, String animalId) {
        Owner owner = ownerRepository.findById(ownerId).get();
        deleteOwnerInAnimal(animalId);
        List<String> newAnimalsList = deleteAnimalInOwnerList(animalId, owner);
        owner.setAnimalsList(newAnimalsList);

        return ownerRepository.save(owner);
    }

    private List<String> deleteAnimalInOwnerList(String animalId, Owner owner) {
        List<String> newAnimalsList = owner.getAnimalsList()
                .stream()
                .filter(s -> !s.equals(animalId))
                .collect(Collectors.toList());
        return newAnimalsList;
    }

    private void deleteOwnerInAnimal(String animalId) {
        Animals animals = animalsRepo.findById(animalId).get();
        animals.setOwnerId(null);
        animalsRepo.save(animals);
    }
}
