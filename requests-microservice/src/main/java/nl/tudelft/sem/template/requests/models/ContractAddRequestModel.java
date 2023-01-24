package nl.tudelft.sem.template.requests.models;

import lombok.Data;

/*
Example:
{
    "nameEmployer": "Sem",
    "nameCandidate": "Tommy",
    "addressCandidate": "Leiden",
    "contractDuration": "PART_TIME",
    "workHours": 10,
    "vacationDays": 10,
    "pensionScheme": "Scheme",
    "salaryScale": "Scale",
    "salarySteps": "Steps",
    "travelAllowance": 50.55,
    "insurance": true
}
 */

@Data
public class ContractAddRequestModel {
    private String status;
    private String nameEmployer;
    private String nameCandidate;
    private String addressCandidate;
    private String contractDuration;
    private int workHours;
    private int vacationDays;
    private String pensionScheme;
    private String salaryScale;
    private String salarySteps;
    private double travelAllowance;
    private Boolean insurance;
}
