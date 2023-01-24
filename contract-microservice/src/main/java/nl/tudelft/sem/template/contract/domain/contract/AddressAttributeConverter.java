package nl.tudelft.sem.template.contract.domain.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA Converter for the address value object.
 */
@Converter
public class AddressAttributeConverter implements AttributeConverter<Address, String> {

    @Override
    public String convertToDatabaseColumn(Address attribute) {
        return attribute.toString();
    }

    @Override
    public Address convertToEntityAttribute(String dbData) {
        return new Address(dbData);
    }

}