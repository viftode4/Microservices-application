package nl.tudelft.sem.template.contract.domain.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA Converter for the work hours value object.
 */
@Converter
public class WorkHoursAttributeConverter implements AttributeConverter<WorkHours, Integer> {

    @Override
    public Integer convertToDatabaseColumn(WorkHours attribute) {
        return attribute.getHours();
    }

    @Override
    public WorkHours convertToEntityAttribute(Integer dbData) {
        return new WorkHours(dbData);
    }

}