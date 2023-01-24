package nl.tudelft.sem.template.authentication.domain.user;

import javax.persistence.AttributeConverter;

class RoleAttributeConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.toString();
    }

    @Override
    public Role convertToEntityAttribute(String s) {
        return Role.valueOf(s);
    }
}
