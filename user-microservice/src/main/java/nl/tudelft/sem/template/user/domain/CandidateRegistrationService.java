package nl.tudelft.sem.template.user.domain;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 *  Service that connects the application with the user database.
 */
@Service
public class CandidateRegistrationService {

    private final transient UserRepository userRepository;

    public CandidateRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new User candidate and stores it in the database.
     *
     * @param firstName - the first name of the new candidate
     * @param lastName - the last name of the new candidate
     * @param address - the address of the new candidate
     * @param netId - the netId of the new candidate
     * @return the newly created User
     * @throws Exception - in case of database errors
     */
    public User registerCandidate(String firstName, String lastName, String address, String netId)
        throws Exception {
        User candidate = new User(firstName, lastName, address, netId, UserRole.Candidate);
        userRepository.saveAndFlush(candidate);
        return candidate;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
