package nl.tudelft.sem.template.authentication.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthManagerTests {
    private transient RoleManager roleManager;

    @BeforeEach
    public void setup() {
        roleManager = new RoleManager();
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
        String actual = roleManager.getNetId();

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

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
        String actual = roleManager.getRole();

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
