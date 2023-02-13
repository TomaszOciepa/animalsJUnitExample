package com.tom.animalsy.service;

import com.tom.animalsy.Repository.AnimalsRepo;
import com.tom.animalsy.model.*;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AnimalsServiceImplTest {

    @Test
    void getAnimalsShouldBeReturnAllAnimals() {
        //given
        List<Animals> animals = prepareAnimalsData();
        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        AnimalsServiceImpl animalsService = new AnimalsServiceImpl(animalsRepo);
        given(animalsRepo.findAll()).willReturn(animals);

        //when
        List<Animals> animalsList = animalsService.getAnimals();

        //then
        assertThat(animalsList, hasSize(2));

    }

    @Test
    void getAnimalsShouldBeReturnEmptyList() {
        //given
        List<Animals> emptyAnimalList = new ArrayList<>();
        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        AnimalsServiceImpl animalsService = new AnimalsServiceImpl(animalsRepo);
        given(animalsRepo.findAll()).willReturn(emptyAnimalList);

        //when
        List<Animals> animalsList = animalsService.getAnimals();

        //then
        assertThat(animalsList, empty());
    }

    @Test
    void getAnimalShouldBeReturnAnimalById() {
        //given
        List<Animals> animalsList = prepareAnimalsData();
        Animals animal = animalsList.stream().filter(a -> a.getId().equals("1")).collect(Collectors.toList()).get(0);
        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        AnimalsServiceImpl animalsService = new AnimalsServiceImpl(animalsRepo);
        given(animalsRepo.findById(animal.getId())).willReturn(Optional.of(animal));

        //when
        Animals animalById1 = animalsService.getAnimal("1");

        //then
        assertThat(animalById1, equalTo(animal));
    }

    @Test
    void addAnimalShouldReturnNewAnimal() {
        //given
        Animals newAnimal = new Animals("3", "Misiek", "Dog", 12, Gender.MALE, null);
        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        AnimalsServiceImpl animalsService = new AnimalsServiceImpl(animalsRepo);
        given(animalsRepo.insert(newAnimal)).willReturn(newAnimal);

        //when
        Animals animalById1 = animalsService.addAnimal(newAnimal);

        //then
        assertThat(animalById1, equalTo(newAnimal));
    }

    @Test
    void putAnimalShouldReturnAnimalWithNewAgeValue() {
        //given
        List<Animals> animalsList = prepareAnimalsData();
        Animals newAnimal = new Animals("1", "Mango", "Dog", 2, Gender.MALE, null);
        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        AnimalsServiceImpl animalsServiceImpl = new AnimalsServiceImpl(animalsRepo);
        given(animalsRepo.findById("1")).willReturn(Optional.ofNullable(animalsList.get(0)));
        given(animalsRepo.save(newAnimal)).willReturn(newAnimal);

        //when
        Animals animal = animalsServiceImpl.putAnimal("1", newAnimal);

        //then
        assertThat(animal, equalTo(newAnimal));
        assertThat(animal.getAge(), is(2));
    }

    @Test
    void findAnimalsByOwnerId() {
        //given

        List<Animals> animalsList = prepareAnimalsData();
        List<Animals> collect = animalsList
                .stream()
                .filter(animals -> animals.getOwnerId().equals("99dc4"))
                .collect(Collectors.toList());

        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        AnimalsServiceImpl animalsServiceImpl = new AnimalsServiceImpl(animalsRepo);
        given(animalsRepo.findByOwnerId("99dc4")).willReturn(collect);

        //when
        List<Animals> animals = animalsServiceImpl.findAnimalsByOwnerId("99dc4");

        //then
        assertThat(animals, equalTo(collect));

    }


    List<Animals> prepareAnimalsData() {
        Address address = new Address("Poland", "Gdańsk", "80-283");
        List<String> animalIdList = new ArrayList<>();
        Owner owner1 = new Owner("99dc4", "Tomek", "Ociepa", "tom@wp.pl", address, LocalDateTime.now(), animalIdList);
        Owner owner2 = new Owner("99ooo", "Klaudia", "Quirini", "kla@wp.pl", address, LocalDateTime.now(), animalIdList);
        Animals animal1 = new Animals("1", "Mango", "Dog", 1, Gender.MALE, owner1.getId());
        Animals animal2 = new Animals("2", "Felix", "Cat", 10, Gender.MALE, owner2.getId());

        animalIdList.add(animal1.getId());

        List<Animals> animalsList = new ArrayList<>();
        animalsList.add(animal1);
        animalsList.add(animal2);

        return animalsList;
    }

}