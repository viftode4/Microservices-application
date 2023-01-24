package nl.tudelft.sem.template.example.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Verifies the JWT token in the request for validity.
 */
@Component
public class JwtTokenVerifier {
    @Value("${jwt.secret}")  // automatically loads jwt.secret from resources/application.properties
    private transient String jwtSecret;

    /**
     * Validate the JWT token for expiration.
     */
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public String getNetIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

   //get role from token
    public String getRoleFromToken(String token) {
        //get the roles from the body of the token
        return getClaimFromToken(token, "roles").get(0).toString();
    }

    public Date getExpirationDateFromToken(String token) {
        //get the expiration date from the body of the token
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private List<String> getClaimFromToken(String token, String what) {
        final Claims claims = getClaims(token);
        return (List<String>) claims.get(what);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}
