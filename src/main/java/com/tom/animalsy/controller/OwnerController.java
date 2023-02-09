package com.tom.animalsy.controller;

import com.tom.animalsy.model.Owner;
import com.tom.animalsy.service.OwnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerService ownerService;


    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }


    @GetMapping
    public List<Owner> getAllOwners(){
        return ownerService.getOwners();
    }

    @GetMapping("/{id}")
    public Owner getOwnerById(@PathVariable String id){
        return ownerService.getOwnerById(id);
    }

    @PostMapping
    public Owner addOwner(@RequestBody Owner owner){
        return ownerService.addOwner(owner);
    }

    @DeleteMapping
    public void deleteOwner(String id){
        ownerService.deleteOwner(id);
    }

    @PutMapping("/{id}")
    public Owner updateOwner(@PathVariable String id, @RequestBody Owner owner){
        return ownerService.putOwner(id, owner);
    }

    @PostMapping("/{ownerId}/animal/{animalId}")
    public Owner addAnimalToOwner(@PathVariable String ownerId, @PathVariable String animalId){
        return ownerService.addAnimal(ownerId, animalId);
    }

    @DeleteMapping("/{ownerId}/animal/{animalId}")
    public Owner deleteAnimalToOwner(@PathVariable String ownerId, @PathVariable String animalId){
        return  ownerService.deleteAnimal(ownerId, animalId);
    }

}
