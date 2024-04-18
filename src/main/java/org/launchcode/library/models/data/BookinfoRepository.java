package org.launchcode.library.models.data;


import org.launchcode.library.models.Bookinfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookinfoRepository extends CrudRepository<Bookinfo, Integer>  {
}
