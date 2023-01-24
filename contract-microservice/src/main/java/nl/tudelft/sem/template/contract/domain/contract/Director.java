package nl.tudelft.sem.template.contract.domain.contract;

public class Director {
    transient Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    /**
     * Creates the contract.
     *
     * @param nameEmployer the name of the employer
     * @param nameCandidate the name of the potential employee
     * @param addressCandidate the address of the candidate
     * @param contractDuration the duration on the contract
     * @param workHours the work hours per week
     * @param vacationDays the amount of vacation days
     * @param pensionScheme the pension scheme
     * @param salary the salary
     * @param travelAllowance the travel allowance
     * @param insurance the insurance plan
     */
    public void constructCustomContract(Name nameEmployer, Name nameCandidate, Address addressCandidate,
                                        ContractDuration contractDuration, WorkHours workHours,
                                        VacationDays vacationDays, PensionScheme pensionScheme, Salary salary,
                                        TravelAllowance travelAllowance, Insurance insurance) {

        builder.setAttributes(nameEmployer,
                nameCandidate,
                addressCandidate,
                contractDuration,
                workHours,
                vacationDays,
                pensionScheme,
                salary,
                travelAllowance,
                insurance);
        //        builder.setNameCandidate(nameCandidate);
        //        builder.setNameEmployer(nameEmployer);
        //        builder.setAddressCandidate(addressCandidate);
        //        builder.setContractDuration(contractDuration);
        //        builder.setWorkHours(workHours);
        //        builder.setVacationDays(vacationDays);
        //        builder.setPensionScheme(pensionScheme);
        //        builder.setSalary(salary);
        //        builder.setTravelAllowance(travelAllowance);
        //        builder.setInsurance(insurance);
    }
}
