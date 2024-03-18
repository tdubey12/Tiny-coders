package org.launchcode.library.models.data;

import org.launchcode.library.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository  extends CrudRepository<Book, Integer> {
}
