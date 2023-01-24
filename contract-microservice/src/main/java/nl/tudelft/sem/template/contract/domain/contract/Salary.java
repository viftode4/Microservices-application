package nl.tudelft.sem.template.contract.domain.contract;

import lombok.EqualsAndHashCode;

/**
 * A DDD value object representing a salary in our domain.
 */
@EqualsAndHashCode
public class Salary {
    private final transient String scale;
    private final transient String steps;

    /**
     * Instantiates a salary object with the salary scale and salary steps.
     *
     * @param scale the salary scale
     * @param steps the salary steps
     */
    public Salary(String scale, String steps) {
        // validate NetID
        this.scale = scale;
        this.steps = steps;
    }

    public String getScale() {
        return scale;
    }

    public String getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return scale + "-" + steps;
    }
}
