package nl.tudelft.sem.template.user.models;

import lombok.Data;

@Data
public class RegistrationRequestModel {
    private String netId;
    private String password;

    public RegistrationRequestModel(String netId, String password) {
        this.netId = netId;
        this.password = password;
    }
}