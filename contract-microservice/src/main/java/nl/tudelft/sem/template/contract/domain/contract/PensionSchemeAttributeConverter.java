package nl.tudelft.sem.template.contract.domain.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA Converter for the pension scheme value object.
 */
@Converter
public class PensionSchemeAttributeConverter implements AttributeConverter<PensionScheme, String> {

    @Override
    public String convertToDatabaseColumn(PensionScheme attribute) {
        return attribute.toString();
    }

    @Override
    public PensionScheme convertToEntityAttribute(String dbData) {
        return new PensionScheme(dbData);
    }

}