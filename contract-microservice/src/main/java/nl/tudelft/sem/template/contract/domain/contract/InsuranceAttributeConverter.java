package nl.tudelft.sem.template.contract.domain.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA Converter for the insurance value object.
 */
@Converter
public class InsuranceAttributeConverter implements AttributeConverter<Insurance, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(Insurance attribute) {
        return attribute.getHasInsurance();
    }

    @Override
    public Insurance convertToEntityAttribute(Boolean dbData) {
        return new Insurance(dbData);
    }

}
