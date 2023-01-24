package nl.tudelft.sem.template.contract.domain.contract;

import lombok.EqualsAndHashCode;

/**
 * A DDD value object representing a pension scheme in our domain.
 */
@EqualsAndHashCode
public class PensionScheme {
    private final transient String scheme;

    public PensionScheme(String scheme) {
        // validate NetID
        this.scheme = scheme;
    }

    @Override
    public String toString() {
        return scheme;
    }
}