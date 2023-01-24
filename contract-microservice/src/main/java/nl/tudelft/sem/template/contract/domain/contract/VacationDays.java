package nl.tudelft.sem.template.contract.domain.contract;

import lombok.EqualsAndHashCode;

/**
 * A DDD value object representing vacation days in our domain.
 */
@EqualsAndHashCode
public class VacationDays {
    private final transient int days;

    public VacationDays(int days) {
        // validate NetID
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    @Override
    public String toString() {
        return Integer.toString(days);
    }
}
