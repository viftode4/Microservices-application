package nl.tudelft.sem.template.user.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Find user by netId
     * @param netId - netId of the user ot be found
     * @return Optional of User
     */
    Optional<User> findByNetId(String netId);
}
