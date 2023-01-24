package nl.tudelft.sem.template.requests.domain.requests;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RequestTypeAttributeConverter implements AttributeConverter<RequestType, String> {

    @Override
    public String convertToDatabaseColumn(RequestType attribute) {
        return attribute.toString();
    }

    @Override
    public RequestType convertToEntityAttribute(String dbData) {
        return RequestType.valueOf(dbData);
    }


}