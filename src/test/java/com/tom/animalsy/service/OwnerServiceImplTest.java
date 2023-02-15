package com.tom.animalsy.service;

import com.tom.animalsy.Repository.AnimalsRepo;
import com.tom.animalsy.Repository.OwnerRepository;
import com.tom.animalsy.model.Address;
import com.tom.animalsy.model.Animals;
import com.tom.animalsy.model.Gender;
import com.tom.animalsy.model.Owner;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class OwnerServiceImplTest {

    @Test
    void getOwners() {
        //given
        List<Owner> prepareOwnerList = prepareOwnersData();
        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerRepository, animalsRepo);
        given(ownerRepository.findAll()).willReturn(prepareOwnerList);

        //when
        List<Owner> owners = ownerService.getOwners();

        //then
        assertThat(owners, equalTo(prepareOwnerList));
        assertThat(owners, hasSize(2));

    }

    @Test
    void getOwnerById() {
        //given
        List<Owner> prepareOwnerList = prepareOwnersData();
        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerRepository, animalsRepo);
        given(ownerRepository.findById("21")).willReturn(prepareOwnerList
                .stream()
                .filter(o -> o.getId().equals("21")).findFirst()
        );

        //when
        Owner ownerById = ownerService.getOwnerById("21");

        //then
        assertThat(ownerById.getId(), equalTo("21"));

    }

    @Test
    void addOwner() {
        //given
//        List<Owner> prepareOwnerList = prepareOwnersData();
        Owner owner = new Owner("31", "Klaudia", "Quirini", "kla@wp.pl", null, LocalDateTime.now(), null);
        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerRepository, animalsRepo);
        given(ownerRepository.save(owner)).willReturn(owner);

        //when
        Owner newOwner = ownerService.addOwner(owner);

        //then
        assertThat(newOwner, equalTo(owner));
        assertThat(newOwner.getId(), is("31"));
    }

    @Test
    void putOwner() {
        //given
        List<Owner> prepareOwnerList = prepareOwnersData();
        Owner owner = prepareOwnerList.get(0);
        owner.setEmail("Klaudi@wp.pl");

        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerRepository, animalsRepo);
        given(ownerRepository.findById("21")).willReturn(prepareOwnerList
                .stream()
                .filter(o -> o.getId().equals("21"))
                .findFirst()
        );

        given(ownerRepository.save(owner)).willReturn(owner);

        //when
        Owner updateOwner = ownerService.putOwner("21", owner);

        //then
        assertThat(updateOwner, equalTo(owner));
    }

    @Test
    void addAnimal() {
        //given
        Animals animal1 = new Animals("1", "Mango", "Dog", 1, Gender.MALE, null);
        List<Owner> owners = prepareOwnersData();
        Owner owner = owners
                .stream()
                .filter(o -> o.getId().equals("21"))
                .findFirst().get();

        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerRepository, animalsRepo);
        given(ownerRepository.findById("21")).willReturn(Optional.of(owner));
        given(animalsRepo.findById("1")).willReturn(Optional.of(animal1));
        given(ownerRepository.save(owner)).willReturn(owner);

        //when
        Owner ownerWithNewAnimal = ownerService.addAnimal("21", "1");

        //then
        verify(animalsRepo).save(animal1);
        verify(animalsRepo, times(1)).save(animal1); // podajemy, że oczekujemy 1 wykonania metody
        verify(animalsRepo, atLeastOnce()).save(animal1); //oczekujemy, że wywoła się co najmniej 1 raz
        verify(animalsRepo, atLeast(1)).save(animal1); //oczekujemy, że wywoła się co najmniej 1 raz. Podajemy min liczbe wywołań
        verify(ownerRepository).save(owner);
//        verify(ownerRepository, never()).save(owner); // sprawdzamy, że ta metoda nigdy nie zostanie wywyołana
        then(ownerRepository).should().save(owner); // analogiczny zapis jak verify() lecz zgodny z BBDMockito
//        verifyNoMoreInteractions(ownerRepository.save(owner));
//        verifyNoInteractions(ownerRepository);

        // sprawdzenie czy metody wykonują się w danej kolejności
        InOrder inOrder = inOrder(ownerRepository);
        inOrder.verify(ownerRepository).findById("21");
        inOrder.verify(ownerRepository).save(owner);

        assertThat(ownerWithNewAnimal.getAnimalsList()
                .stream()
                .filter(a -> a.equals("1"))
                .findFirst().get(), is("1")
        );
    }

    @Test
    void deleteAnimal() {
        //given
        Animals animal = new Animals("1", "Mango", "Dog", 1, Gender.MALE, "21");
        List<Owner> prepareOwnerData = prepareOwnersData();
        Owner owner = prepareOwnerData
                .stream()
                .filter(o -> o.getId().equals("21"))
                .findFirst()
                .get();
        owner.getAnimalsList().add("1");

        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        AnimalsRepo animalsRepo = mock(AnimalsRepo.class);
        OwnerServiceImpl ownerService = new OwnerServiceImpl(ownerRepository, animalsRepo);
        given(ownerRepository.findById("21")).willReturn(Optional.of(owner));
        given(animalsRepo.findById("1")).willReturn(Optional.of(animal));
        given(animalsRepo.save(animal)).willReturn(animal);
        given(ownerRepository.save(owner)).willReturn(owner);

        //when
        Owner ownerAfterDeleteAnimal = ownerService.deleteAnimal("21", "1");

        //then
        assertThat(ownerAfterDeleteAnimal.getAnimalsList(), hasSize(0));

    }


    List<Owner> prepareOwnersData() {
        Address address1 = new Address("Poland", "Gdańsk", "80-283");
        Address address2 = new Address("Poland", "Chojnice", "80-600");
        List<String> animalIdList = new ArrayList<>();
        Owner owner1 = new Owner("21", "Klaudia", "Quirini", "kla@wp.pl", address1, LocalDateTime.now(), animalIdList);
        Owner owner2 = new Owner("23", "Tomek", "Ociepa", "tom@wp.pl", address2, LocalDateTime.now(), animalIdList);

        List<Owner> ownerList = new ArrayList<>();
        ownerList.add(owner1);
        ownerList.add(owner2);
        return ownerList;

    }
}