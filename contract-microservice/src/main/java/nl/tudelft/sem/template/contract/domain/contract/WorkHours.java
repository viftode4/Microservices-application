package nl.tudelft.sem.template.contract.domain.contract;

import lombok.EqualsAndHashCode;

/**
 * A DDD value object representing work hours per week in our domain.
 */
@EqualsAndHashCode
public class WorkHours {
    private final transient int hours;

    public WorkHours(int hours) {
        // validate NetID
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public String toString() {
        return Integer.toString(hours);
    }
}