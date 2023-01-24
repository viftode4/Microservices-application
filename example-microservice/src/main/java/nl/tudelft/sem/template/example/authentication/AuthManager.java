package nl.tudelft.sem.template.example.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Authentication Manager.
 */
@Component
public class AuthManager {
    /**
     * Interfaces with spring security to get the name of the user in the current context.
     *
     * @return The name of the user.
     */
    public String getNetId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public String getRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().collect(Collectors.toList()).get(0).getAuthority();
    }
}
