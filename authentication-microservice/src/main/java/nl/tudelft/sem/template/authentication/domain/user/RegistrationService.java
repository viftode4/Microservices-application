package nl.tudelft.sem.template.authentication.domain.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * A DDD service for registering a new user.
 */
@Service
public class RegistrationService {
    private final transient UserRepository userRepository;
    private final transient PasswordHashingService passwordHashingService;

    /**
     * Instantiates a new UserService.
     *
     * @param userRepository  the user repository
     * @param passwordHashingService the password encoder
     */
    public RegistrationService(UserRepository userRepository, PasswordHashingService passwordHashingService) {
        this.userRepository = userRepository;
        this.passwordHashingService = passwordHashingService;
    }

    /**
     * Register a new user.
     *
     * @param netId    The NetID of the user
     * @param password The password of the user
     * @throws Exception if the user already exists
     */
    public AppUser registerUser(NetId netId, Password password) throws Exception {

        if (checkNetIdIsUnique(netId)) {
            // Hash password
            HashedPassword hashedPassword = passwordHashingService.hash(password);

            // Create new account
            AppUser user = new AppUser(netId, hashedPassword);
            userRepository.save(user);

            return user;
        }

        throw new NetIdAlreadyInUseException(netId);
    }

    public boolean checkNetIdIsUnique(NetId netId) {
        return !userRepository.existsByNetId(netId);
    }

    public void registerCandidate(NetId netId, Password password) throws Exception {
        AppUser user = registerUser(netId, password);
        user.setRole(Role.Candidate);
        userRepository.save(user);
    }

    public void setHr(NetId netId) throws Exception {
        AppUser user = userRepository.findByNetId(netId).get();
        user.setRole(Role.HR);
        userRepository.save(user);
    }

    public String findAllHR(){
        Iterable<AppUser> users = userRepository.findAllByRole(Role.HR);
        StringBuilder ret = new StringBuilder();
        for (AppUser user : users) {
            ret.append(user.getNetId().toString()).append(",");
        }
        ret.deleteCharAt(ret.length() - 1);
        return ret.toString();
    }
}
