package nl.tudelft.sem.template.user.models;

import lombok.Data;

@Data
public class RegisterContractModel {
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
	 * Creates a RegisterContractModel object.
	 *
	 * @param emplName	Employer name
	 * @param candName	Candidate name
	 * @param candAddr	Candidate address
	 * @param contrDur	Contract duration
	 * @param workHours	Working hours
	 * @param vacDays	Vacation days
	 * @param scheme	Pension scheme
	 * @param scale		Salary scale
	 * @param steps		Salary steps
	 * @param allowance	Travel allowance
	 * @param insurance	Insurance coverage
	 */
	public RegisterContractModel(String emplName,
								 String candName,
								 String candAddr,
								 String contrDur,
								 int workHours,
								 int vacDays,
								 String scheme,
								 String scale,
								 String steps,
								 double allowance,
								 Boolean insurance) {
		this.nameEmployer = emplName;
		this.nameCandidate = candName;
		this.addressCandidate = candAddr;
		this.contractDuration = contrDur;
		this.workHours = workHours;
		this.vacationDays = vacDays;
		this.pensionScheme = scheme;
		this.salaryScale = scale;
		this.salarySteps = steps;
		this.travelAllowance = allowance;
		this.insurance = insurance;
	}
}
