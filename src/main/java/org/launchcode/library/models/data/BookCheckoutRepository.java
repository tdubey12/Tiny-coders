package org.launchcode.library.models.data;

import org.launchcode.library.models.Book;
import org.launchcode.library.models.BookCheckout;
import org.springframework.data.repository.CrudRepository;

public interface BookCheckoutRepository extends CrudRepository<BookCheckout, Integer> {
    BookCheckout findByBookIdAndStudentIdAndIsCheckout(int bookId, int studentId,boolean isCheckout);
}
