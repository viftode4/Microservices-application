package nl.tudelft.sem.template.user.models;

import lombok.Data;

@Data
public class RegisterCandidateModel {
    private String netId;
    private String firstName;
    private String lastName;
    private String address;
    private String password;
}
