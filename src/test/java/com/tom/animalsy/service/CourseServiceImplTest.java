package com.tom.animalsy.service;

import com.tom.animalsy.Repository.CourseRepository;
import com.tom.animalsy.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CourseServiceImplTest {

    @Test
    void getCoursesShouldBeReturnAllCourses() {
        //given
        List<Course> courseDataPrepareList = prepareCourseData();
        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseServiceImpl courseService = new CourseServiceImpl(courseRepository);
        given(courseRepository.findAll()).willReturn(courseDataPrepareList);

        //when
        List<Course> courseList = courseService.getCourses();

        //then
        assertThat(courseList, equalTo(courseDataPrepareList));
        assertThat(courseList, hasSize(2));
    }

    @Test
    void getCourseById() {
        //given
        List<Course> courseDataPrepareList = prepareCourseData();
        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseServiceImpl courseService = new CourseServiceImpl(courseRepository);
        given(courseRepository.findById("1dq")).willReturn(courseDataPrepareList
                .stream()
                .filter(course -> course.getId().equals("1dq"))
                .findFirst()
        );

        //when
        Course wantedCourse = courseService.getCourseById("1dq");

        //then
        assertThat(wantedCourse.getId(), equalTo("1dq"));
        assertThat(wantedCourse.getName(), equalTo("Kurs dla piesków"));

    }

    @Test
    void addCourse() {
        //given
        List<String> list = Arrays.asList();
        Course newCourse = new Course("231d", "Nowy Kurs", LocalDateTime.now(), LocalDateTime.now().plusMonths(1), list);
        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseServiceImpl courseService = new CourseServiceImpl(courseRepository);
        given(courseRepository.save(newCourse)).willReturn(newCourse);

        //when
        Course course = courseService.addCourse(newCourse);

        //then
        assertThat(course, equalTo(newCourse));
    }



    @Test
    void putCourse() {
        //given
        List<Course> courseDataPrepareList = prepareCourseData();
        List<String> list = Arrays.asList();
        Course course = new Course("1dq", "Kurs dla piesków (update)", LocalDateTime.now(), LocalDateTime.now().plusMonths(1), list);
        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseServiceImpl courseService = new CourseServiceImpl(courseRepository);
        given(courseRepository.findById("1dq")).willReturn(Optional.ofNullable(courseDataPrepareList.get(0)));
        given(courseRepository.save(course)).willReturn(course);

        //when
        Course courseUpdate = courseService.putCourse("1dq", course);

        //then
        assertThat(courseUpdate, equalTo(course));


    }

    @Test
    void addAnimal() {
        //given
        List<String> animalIdList = new ArrayList<>();
        Course course = new Course("1dq","Kurs dla piesków", LocalDateTime.now().minusMonths(2), LocalDateTime.now().plusMonths(1), animalIdList);
        Animals animal = new Animals("99e", "Franklin", "Żółw", 1, Gender.MALE, null);

        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseServiceImpl courseService = new CourseServiceImpl(courseRepository);
        given(courseRepository.findById("1dq")).willReturn(Optional.of(course));
        given(courseRepository.save(course)).willReturn(course);

        //when
        Course curseAfterAddAnimal = courseService.addAnimal("1dq", "99e");

        //then
        assertThat(curseAfterAddAnimal.getAnimalLists().size(), is(1));


    }

    @Test
    void deleteAnimal() {
        //given
        Animals animal = new Animals("99e", "Franklin", "Żółw", 1, Gender.MALE, null);
        List<String> animalIdList = new ArrayList<>();
        animalIdList.add(animal.getId());
        Course course = new Course("1dq","Kurs dla piesków", LocalDateTime.now().minusMonths(2), LocalDateTime.now().plusMonths(1), animalIdList);

        CourseRepository courseRepository = mock(CourseRepository.class);
        CourseServiceImpl courseService = new CourseServiceImpl(courseRepository);
        given(courseRepository.findById("1dq")).willReturn(Optional.of(course));
        given(courseRepository.save(course)).willReturn(course);

        //when
        Course courseAfterDeleteAnimal = courseService.deleteAnimal("1dq", "99e");

        //then
        assertThat(courseAfterDeleteAnimal.getAnimalLists(), hasSize(0));

    }

    List<Course> prepareCourseData() {

        List<String> animalIdList1 = new ArrayList<>();
        List<String> animalIdList2 = new ArrayList<>();

        Address address = new Address("Poland", "Gdańsk", "80-283");
        Owner owner1 = new Owner("99dc4", "Tomek", "Ociepa", "tom@wp.pl", address, LocalDateTime.now(), animalIdList2);
        Owner owner2 = new Owner("99ooo", "Klaudia", "Quirini", "kla@wp.pl", address, LocalDateTime.now(), animalIdList1);

        Animals animal1 = new Animals("1", "Mango", "Dog", 1, Gender.MALE, owner1.getId());
        Animals animal2 = new Animals("2", "Felix", "Cat", 10, Gender.MALE, owner2.getId());
        animalIdList1.add(animal1.getId());
        animalIdList2.add(animal2.getId());


        Course course1 = new Course("1dq","Kurs dla piesków", LocalDateTime.now().minusMonths(2), LocalDateTime.now().plusMonths(1), animalIdList1);
        Course course2 = new Course("4dq","Kurs dla kotków", LocalDateTime.now().minusMonths(2), LocalDateTime.now().plusMonths(1), animalIdList2);

        List<Course> courseList = new ArrayList<>();
        courseList.add(course1);
        courseList.add(course2);
        return courseList;

    }
}