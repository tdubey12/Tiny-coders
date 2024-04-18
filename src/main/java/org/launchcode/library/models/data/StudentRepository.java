package org.launchcode.library.models.data;

import org.launchcode.library.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
    List<Student> findByLastname(String lastName);
    List<Student> findByFirstname(String lastName);
    Student findByContactEmail(String email);
}