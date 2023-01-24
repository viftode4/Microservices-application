package nl.tudelft.sem.template.contract.domain.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA Converter for the name value object.
 */
@Converter
public class NameAttributeConverter implements AttributeConverter<Name, String> {

    @Override
    public String convertToDatabaseColumn(Name attribute) {
        return attribute.toString();
    }

    @Override
    public Name convertToEntityAttribute(String dbData) {
        return new Name(dbData);
    }

}