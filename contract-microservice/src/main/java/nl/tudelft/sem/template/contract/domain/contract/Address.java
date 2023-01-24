package nl.tudelft.sem.template.contract.domain.contract;

import lombok.EqualsAndHashCode;

/**
 * A DDD value object representing an address in our domain.
 */
@EqualsAndHashCode
public class Address {
    private final transient String addressString;

    public Address(String addressString) {
        // validate NetID
        this.addressString = addressString;
    }

    @Override
    public String toString() {
        return addressString;
    }
}
