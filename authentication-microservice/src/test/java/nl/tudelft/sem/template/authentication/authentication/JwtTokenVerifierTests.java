package nl.tudelft.sem.template.authentication.authentication;

import io.jsonwebtoken.*;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class JwtTokenVerifierTests {
    private transient JwtTokenVerifier jwtTokenVerifier;

    private final String secret = "testSecret123";

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        jwtTokenVerifier = new JwtTokenVerifier();
        this.injectSecret(secret);
    }

    @Test
    public void validateNonExpiredToken() {
        // Arrange
        String token = generateToken(secret, "user123", -10_000_000, 10_000_000);

        // Act
        boolean actual = jwtTokenVerifier.validateToken(token);

        // Assert
        assertThat(actual).isTrue();
    }

    @Test
    public void validateExpiredToken() {
        // Arrange
        String token = generateToken(secret, "user123", -10_000_000, -5_000_000);

        // Act
        ThrowableAssert.ThrowingCallable action = () -> jwtTokenVerifier.validateToken(token);

        // Assert
        assertThatExceptionOfType(ExpiredJwtException.class)
                .isThrownBy(action);
    }

    @Test
    public void validateTokenIncorrectSignature() {
        // Arrange
        String token = generateToken("incorrectSecret", "user123", -10_000_000, 10_000_000);

        // Act
        ThrowableAssert.ThrowingCallable action = () -> jwtTokenVerifier.validateToken(token);

        // Assert
        assertThatExceptionOfType(SignatureException.class)
                .isThrownBy(action);
    }

    @Test
    public void validateMalformedToken() {
        // Arrange
        String token = "malformedtoken";

        // Act
        ThrowableAssert.ThrowingCallable action = () -> jwtTokenVerifier.validateToken(token);

        // Assert
        assertThatExceptionOfType(MalformedJwtException.class)
                .isThrownBy(action);
    }

    @Test
    public void parseNetid() {
        // Arrange
        String expected = "user123";
        String token = generateToken(secret, expected, -10_000_000, 10_000_000);

        // Act
        String actual = jwtTokenVerifier.getNetIdFromToken(token);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    // get role from token
    @Test
    public void parseRole() {
        // Arrange
        String expected = "student";
        String token = generateTokenRole(secret , expected, -10_000_000, 10_000_000);

        // Act
        String actual = jwtTokenVerifier.getRoleFromToken(token);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }


    private String generateToken(String jwtSecret, String netid, long issuanceOffset, long expirationOffset) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(netid)
                .setIssuedAt(new Date(System.currentTimeMillis() + issuanceOffset))
                .setExpiration(new Date(System.currentTimeMillis() + expirationOffset))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    private String generateTokenRole(String jwtSecret, String role, long issuanceOffset, long expirationOffset) {
        var roles = List.of(role);

        return Jwts.builder().claim("roles", roles)
                .setIssuedAt(new Date(System.currentTimeMillis() + issuanceOffset))
                .setExpiration(new Date(System.currentTimeMillis() + expirationOffset))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    private void injectSecret(String secret) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = jwtTokenVerifier.getClass().getDeclaredField("jwtSecret");
        declaredField.setAccessible(true);
        declaredField.set(jwtTokenVerifier, secret);
    }
}