package com.tom.animalsy.service;

import com.tom.animalsy.Repository.CourseRepository;
import com.tom.animalsy.model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(String id) {
        return courseRepository.findById(id).get();
    }

    @Override
    public Course addCourse(Course course) {

        List<String> animalsList = new ArrayList<>();
        course.setAnimalLists(animalsList);
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course putCourse(String id, Course course) {
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            optionalCourse.get().setName(course.getName());
            optionalCourse.get().setStartDate(course.getStartDate());
            optionalCourse.get().setEndDate(course.getEndDate());
            optionalCourse.get().setAnimalLists(course.getAnimalLists());


            return courseRepository.save(optionalCourse.get());
        } else {
            return optionalCourse.get();
        }
    }

    @Override
    public Course addAnimal(String courseId, String animalId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            List<String> animalsList = courseOptional.get().getAnimalLists();
            animalsList.add(animalId);
            courseOptional.get().setAnimalLists(animalsList);
            return courseRepository.save(courseOptional.get());
        }
        return courseOptional.get();
    }

    @Override
    public Course deleteAnimal(String courseId, String animalId) {
        Course course = courseRepository.findById(courseId).get();
        List<String> animalList = course.getAnimalLists()
                .stream()
                .filter(s -> !s.equals(animalId))
                .collect(Collectors.toList());

        course.setAnimalLists(animalList);
        return courseRepository.save(course);
    }
}
