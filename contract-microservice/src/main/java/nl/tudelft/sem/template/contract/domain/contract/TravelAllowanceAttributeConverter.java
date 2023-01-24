package nl.tudelft.sem.template.contract.domain.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA Converter for the travel allowance value object.
 */
@Converter
public class TravelAllowanceAttributeConverter implements AttributeConverter<TravelAllowance, Double> {

    @Override
    public Double convertToDatabaseColumn(TravelAllowance attribute) {
        return attribute.getAllowance();
    }

    @Override
    public TravelAllowance convertToEntityAttribute(Double dbData) {
        return new TravelAllowance(dbData);
    }

}
