package org.launchcode.library.models.data;

import org.launchcode.library.models.Book;
import org.launchcode.library.models.Student;
import org.launchcode.library.models.StudentBook;
import org.launchcode.library.models.StudentBookId;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface StudentBookRepository extends CrudRepository<StudentBook, StudentBookId> {
    List<StudentBook> findByBook(Book book);
    List<StudentBook>findByStudent(Student student);
}
