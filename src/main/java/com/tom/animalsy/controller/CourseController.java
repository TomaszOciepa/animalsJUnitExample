package com.tom.animalsy.controller;

import com.tom.animalsy.model.Course;
import com.tom.animalsy.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getCourses(){
        return courseService.getCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById( @PathVariable String id){
        return courseService.getCourseById(id);
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable String id){
        courseService.deleteCourse(id);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable String id, @RequestBody Course course){
        return courseService.putCourse(id, course);
    }

    @PostMapping("/{courseId}/{animalId}")
    public Course addAnimal(@PathVariable String courseId, @PathVariable String animalId){
        return courseService.addAnimal(courseId, animalId);
    }

    @DeleteMapping("/{courseId}/{animalId}")
    public Course deleteAnimal(@PathVariable String courseId, @PathVariable String animalId){
        return courseService.deleteAnimal(courseId, animalId);
    }
}
