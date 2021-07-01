package kea.Repository;

import kea.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepo extends CrudRepository<User, Long> {

}

