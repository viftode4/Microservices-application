package nl.tudelft.sem.template.contract.domain.contract;

import lombok.EqualsAndHashCode;

/**
 * A DDD value object representing travel allowance in our domain.
 */
@EqualsAndHashCode
public class TravelAllowance {
    private final transient double allowance;

    public TravelAllowance(double allowance) {
        // validate NetID
        this.allowance = allowance;
    }

    public double getAllowance() {
        return allowance;
    }

    @Override
    public String toString() {
        return Double.toString(allowance);
    }
}
