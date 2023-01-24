package nl.tudelft.sem.template.requests.domain.requests;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class IdAttributeConverter implements AttributeConverter<Integer, String> {

    /**
     * Converts int ID to database column string.
     *
     * @param attribute  the entity attribute value to be converted
     * @return the string representation of the id
     */
    @Override
    public String convertToDatabaseColumn(Integer attribute) {
        return attribute.toString();
    }

    /**
     * Converts database column string to int ID.
     *
     * @param dbData  the data from the database column to be converted
     * @return the integer value of the id
     */
    @Override
    public Integer convertToEntityAttribute(String dbData) {
        return Integer.parseInt(dbData);
    }

}
