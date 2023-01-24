package nl.tudelft.sem.template.contract.domain.contract;

import java.util.Objects;

/**
 * A DDD value object representing a name in our domain.
 */
public class Name {
    private final transient String nameString;

    public Name(String nameString) {
        // validate NetID
        this.nameString = nameString;
    }

    @Override
    public String toString() {
        return nameString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name name = (Name) o;
        return this.nameString.equals(name.nameString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameString);
    }
}
