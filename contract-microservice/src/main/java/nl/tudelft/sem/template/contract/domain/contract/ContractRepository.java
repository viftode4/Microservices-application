package nl.tudelft.sem.template.contract.domain.contract;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A DDD repository for quering and persisting contract aggregate roots.
 */
@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
    /**
     * Find contract by NameCandidate and AddressCandidate.
     */
    Optional<Contract> findByNameCandidate(Name nameCandidate);

    /**
     * Check if an existing contract already uses a NameCandidate and AddressCandidate.
     */
    boolean existsByNameCandidate(Name nameCandidate);
}
