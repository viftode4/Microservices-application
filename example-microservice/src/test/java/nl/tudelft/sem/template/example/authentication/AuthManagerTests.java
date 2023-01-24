package nl.tudelft.sem.template.example.authentication;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthManagerTests {
    private transient AuthManager authManager;

    @BeforeEach
    public void setup() {
        authManager = new AuthManager();
    }

    @Test
    public void getNetidTest() {
        // Arrange
        String expected = "user123";
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                expected,
                null, List.of() // no credentials and no authorities
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Act
        String actual = authManager.getNetId();

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    //test for getting role
    @Test
    public void getRoleTest() {
        // Arrange
        String expected = "ROLE_USER";
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                null,
                null, List.of(new SimpleGrantedAuthority(expected)) // no credentials and no authorities
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Act
        String actual = authManager.getRole();

        // Assert
        assertThat(actual).isEqualTo(expected);
    }


}
