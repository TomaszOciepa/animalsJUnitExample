package com.tom.animalsy.Repository;

import com.tom.animalsy.model.Owner;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OwnerRepository extends MongoRepository<Owner, String> {
}
