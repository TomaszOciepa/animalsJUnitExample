package com.tom.animalsy.Repository;

import com.tom.animalsy.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
}
