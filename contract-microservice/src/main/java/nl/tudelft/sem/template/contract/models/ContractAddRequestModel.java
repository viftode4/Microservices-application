package nl.tudelft.sem.template.contract.models;

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

    /**
     * Constructor method for ContractAddRequestModel.
     *
     * @param status    The contract status
     * @param nameEmployer  The name of the employer
     * @param nameCandidate The name of the candidate
     * @param addressCandidate  The address of the candidate
     * @param contractDuration  The duration of the contract
     * @param workHours The work hours
     * @param vacationDays  The allowed vacation days
     * @param pensionScheme The pension scheme
     * @param salaryScale   The salary scale
     * @param salarySteps   The salary steps
     * @param travelAllowance   The travel allowance
     * @param insurance If the contract includes insurance
     */
    public ContractAddRequestModel(String status,
                                   String nameEmployer,
                                   String nameCandidate,
                                   String addressCandidate,
                                   String contractDuration,
                                   int workHours,
                                   int vacationDays,
                                   String pensionScheme,
                                   String salaryScale,
                                   String salarySteps,
                                   double travelAllowance,
                                   Boolean insurance) {
        this.status = status;
        this.nameEmployer = nameEmployer;
        this.nameCandidate = nameCandidate;
        this.addressCandidate = addressCandidate;
        this.contractDuration = contractDuration;
        this.workHours = workHours;
        this.vacationDays = vacationDays;
        this.pensionScheme = pensionScheme;
        this.salaryScale = salaryScale;
        this.salarySteps = salarySteps;
        this.travelAllowance = travelAllowance;
        this.insurance = insurance;
    }
}
