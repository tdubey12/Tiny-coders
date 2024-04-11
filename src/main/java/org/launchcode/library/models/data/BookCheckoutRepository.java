package org.launchcode.library.models.data;

import org.launchcode.library.models.BookCheckout;
import org.springframework.data.repository.CrudRepository;

public interface BookCheckoutRepository extends CrudRepository<BookCheckout, Integer> {
}
