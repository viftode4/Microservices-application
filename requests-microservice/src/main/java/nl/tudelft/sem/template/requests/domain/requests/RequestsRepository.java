package nl.tudelft.sem.template.requests.domain.requests;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A DD repository for queueing and persisting request aggregate roots.
 */
@Repository
public interface RequestsRepository extends JpaRepository<Requests, String> {
    /**
     * Find request by ID.
     */
    Optional<Requests> findById(int id);

    /**
     * Check if a request exists.
     */
    boolean existsById(int id);
}
