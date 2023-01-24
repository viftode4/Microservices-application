package nl.tudelft.sem.template.contract.domain.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA Converter for the vacation days value object.
 */
@Converter
public class VacationDaysAttributeConverter implements AttributeConverter<VacationDays, Integer> {

    @Override
    public Integer convertToDatabaseColumn(VacationDays attribute) {
        return attribute.getDays();
    }

    @Override
    public VacationDays convertToEntityAttribute(Integer dbData) {
        return new VacationDays(dbData);
    }

}