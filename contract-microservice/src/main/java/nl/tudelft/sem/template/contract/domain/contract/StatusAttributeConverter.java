package nl.tudelft.sem.template.contract.domain.contract;

import javax.persistence.AttributeConverter;

public class StatusAttributeConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status attribute) {
        return attribute.toString();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        return Status.valueOf(dbData);
    }
}
