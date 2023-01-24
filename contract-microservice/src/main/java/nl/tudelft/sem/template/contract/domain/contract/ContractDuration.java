package nl.tudelft.sem.template.contract.domain.contract;

/**
 * An enum representing the contract duration.
 */
public enum ContractDuration {
    TEMPORARY("TEMPORARY"), PART_TIME("PART_TIME"), PERMANENT("PERMANENT");

    private final String duration;

    ContractDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    /**
     * Returns the enum type from the string representation.
     *
     * @param duration the string representation
     * @return the enum type
     */
    public static ContractDuration fromDuration(String duration) {
        switch (duration) {
            case "TEMPORARY":
                return ContractDuration.TEMPORARY;
            case "PART_TIME":
                return ContractDuration.PART_TIME;
            case "PERMANENT":
                return ContractDuration.PERMANENT;
            default:
                throw new IllegalArgumentException("Duration [" + duration + "] not supported.");
        }
    }
}
