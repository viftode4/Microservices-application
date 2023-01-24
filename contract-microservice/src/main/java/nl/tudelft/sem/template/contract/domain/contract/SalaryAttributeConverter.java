package nl.tudelft.sem.template.contract.domain.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA Converter for the salary value object.
 */
@Converter
public class SalaryAttributeConverter implements AttributeConverter<Salary, String> {

    @Override
    public String convertToDatabaseColumn(Salary attribute) {
        return attribute.toString();
    }

    @Override
    public Salary convertToEntityAttribute(String dbData) {
        return new Salary(dbData.split("-")[0], dbData.split("-")[1]);
    }

}
