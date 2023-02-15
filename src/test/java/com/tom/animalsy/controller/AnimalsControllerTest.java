package com.tom.animalsy.controller;

import com.tom.animalsy.model.Animals;
import com.tom.animalsy.model.Gender;
import com.tom.animalsy.service.AnimalsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


class AnimalsControllerTest {

    private AnimalsService animalsService;
    private AnimalsController animalsController;

    @BeforeEach
    void initializeOrder(){
        animalsService = mock(AnimalsService.class);
        animalsController = new AnimalsController(animalsService);
    }

    @Test
    void getAnimals() {
        //given
        List<Animals> animalsList = new ArrayList<>();
        given(animalsService.getAnimals()).willReturn(animalsList);
        //when
        List<Animals> animals = animalsController.getAnimals();
        //then
        assertThat(animals, equalTo(animalsList));
    }

    @Test
    void getAnimal() {
        //given
        Animals animals = createAnimal();
        given(animalsService.getAnimal("12wq")).willReturn(animals);

        //when
        Animals resultAnimal = animalsController.getAnimal("12wq");

        //then
        assertThat(resultAnimal.getName(), is("Misiek"));
    }

    private Animals createAnimal() {
        Animals animals = new Animals("12wq","Misiek", "Dog", 12, Gender.MALE, null);
        return animals;
    }

    @Test
    void addAnimal() {
        //given
        Animals animal = createAnimal();
        given(animalsService.addAnimal(animal)).willReturn(animal);

        //when
        Animals resultAnimals = animalsController.addAnimal(animal);

        //then
        assertThat(resultAnimals, equalTo(animal));
    }

    @Test
    void deleteAnimal() {
        //given
        willDoNothing().given(animalsService).deleteAnimal(anyString());
        //when
        animalsController.deleteAnimal("12");
        //then
        verify(animalsService).deleteAnimal(anyString());
    }

    @Test
    void putAnimal() {
        //given
        Animals animal = createAnimal();
        given(animalsService.putAnimal("12wq", animal)).willReturn(animal);
        //when
        Animals resultAnimals = animalsController.putAnimal(animal.getId(), animal);
        //then
        assertThat(resultAnimals, equalTo(animal));
    }

    @Test
    void findAnimalsByOwnerId() {
//        //given
        List<Animals> animalsList = new ArrayList<>();
        Animals animal = createAnimal();
        animalsList.add(animal);
        given(animalsService.findAnimalsByOwnerId(anyString())).willReturn(animalsList);

        //when
        List<Animals> result = animalsController.findAnimalsByOwnerId("123");

        //then
        assertThat(result, equalTo(animalsList));
        assertThat(result, hasSize(1));

    }
}