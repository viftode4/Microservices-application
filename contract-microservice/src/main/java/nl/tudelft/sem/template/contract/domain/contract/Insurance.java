package nl.tudelft.sem.template.contract.domain.contract;

import lombok.EqualsAndHashCode;

/**
 * A DDD value object representing insurance in our domain.
 */
@EqualsAndHashCode
public class Insurance {
    private final transient Boolean hasInsurance;

    public Insurance(Boolean hasInsurance) {
        // validate NetID
        this.hasInsurance = hasInsurance;
    }

    public Boolean getHasInsurance() {
        return hasInsurance;
    }

    @Override
    public String toString() {
        return Boolean.toString(hasInsurance);
    }
}

