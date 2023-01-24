package nl.tudelft.sem.template.user.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole attribute) {
        return attribute.toString();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        return UserRole.valueOf(dbData);
    }

}
