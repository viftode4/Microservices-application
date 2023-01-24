package nl.tudelft.sem.template.contract.domain.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA Converter for the contract duration value object.
 */
@Converter
public class ContractDurationAttributeConverter implements AttributeConverter<ContractDuration, String> {

    @Override
    public String convertToDatabaseColumn(ContractDuration attribute) {
        return attribute.getDuration();
    }

    @Override
    public ContractDuration convertToEntityAttribute(String dbData) {
        return ContractDuration.fromDuration(dbData);
    }

}
