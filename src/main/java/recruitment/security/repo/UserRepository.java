package recruitment.security.repo;

import org.springframework.data.repository.CrudRepository;
import recruitment.security.entities.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

}
