package com.tom.animalsy.service;

import com.tom.animalsy.model.Animals;
import com.tom.animalsy.model.Course;

import java.util.List;

public interface CourseService {

    List<Course> getCourses();

    Course getCourseById(String id);

    Course addCourse(Course course);

    void deleteCourse(String id);

    Course putCourse(String id, Course course);

    Course addAnimal(String courseId, String animalId);

    Course deleteAnimal(String courseId, String animalId);

}
