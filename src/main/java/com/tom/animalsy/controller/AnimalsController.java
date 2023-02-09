package com.tom.animalsy.controller;

import com.tom.animalsy.model.Animals;
import com.tom.animalsy.service.AnimalsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalsController {

    private final AnimalsService animalsService;

    public AnimalsController(AnimalsService animalsService) {
        this.animalsService = animalsService;
    }

    @GetMapping
    public List<Animals> getAnimals() {
        return animalsService.getAnimals();
    }

    @GetMapping("/{id}")
    public Animals getAnimal(@PathVariable String id) {
        return animalsService.getAnimal(id);
    }

    @PostMapping
    public Animals addAnimal(@RequestBody Animals animals) {
        return animalsService.addAnimal(animals);
    }

    @DeleteMapping
    public void deleteAnimal(@PathVariable String id) {
        animalsService.deleteAnimal(id);
    }

    @PutMapping("/{id}")
    public Animals putAnimal(@PathVariable String id, @RequestBody Animals animal) {
        return animalsService.putAnimal(id, animal);
    }

    @GetMapping("/owner/{ownerId}")
    public List<Animals> findAnimalsByOwnerId(@PathVariable String ownerId){
        return animalsService.findAnimalsByOwnerId(ownerId);
    }
}
