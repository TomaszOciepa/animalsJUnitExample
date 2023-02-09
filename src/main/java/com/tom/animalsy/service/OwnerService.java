package com.tom.animalsy.service;

import com.tom.animalsy.model.Owner;

import java.util.List;

public interface OwnerService {

    List<Owner> getOwners();

    Owner getOwnerById(String id);

    Owner addOwner(Owner owner);

    void deleteOwner(String id);

    Owner putOwner(String id, Owner owner);

    Owner addAnimal(String ownerId, String animalId);

    Owner deleteAnimal(String ownerId, String animalId);
}
