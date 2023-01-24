package nl.tudelft.sem.template.contract.domain.contract;

public class ContractBuilder implements Builder {
    private transient Name nameEmployer;
    private transient Name nameCandidate;
    private transient Address addressCandidate;
    private transient ContractDuration contractDuration;
    private transient WorkHours workHours;
    private transient VacationDays vacationDays;
    private transient PensionScheme pensionScheme;
    private transient Salary salary;
    private transient TravelAllowance travelAllowance;
    private transient Insurance insurance;

    @Override
    public void setAttributes(Name nameEmployer,
                        Name nameCandidate,
                        Address addressCandidate,
                        ContractDuration contractDuration,
                        WorkHours workHours,
                        VacationDays vacationDays,
                        PensionScheme pensionScheme,
                        Salary salary,
                        TravelAllowance travelAllowance,
                        Insurance insurance) {

        this.nameEmployer = nameEmployer;
        this.nameCandidate = nameCandidate;
        this.addressCandidate = addressCandidate;
        this.contractDuration = contractDuration;
        this.workHours = workHours;
        this.vacationDays = vacationDays;
        this.pensionScheme = pensionScheme;
        this.salary = salary;
        this.travelAllowance = travelAllowance;
        this.insurance = insurance;
    }

    //    @Override
    //    public void setStatus(Status status) {
    //        this.status = status;
    //    }
    //
    //    @Override
    //    public void setNameEmployer(Name nameEmployer) {
    //        this.nameEmployer = nameEmployer;
    //    }
    //
    //    @Override
    //    public void setNameCandidate(Name nameCandidate) {
    //        this.nameCandidate = nameCandidate;
    //    }
    //
    //    @Override
    //    public void setAddressCandidate(Address addressCandidate) {
    //        this.addressCandidate = addressCandidate;
    //    }
    //
    //    @Override
    //    public void setContractDuration(ContractDuration contractDuration) {
    //        this.contractDuration = contractDuration;
    //    }
    //
    //    @Override
    //    public void setWorkHours(WorkHours workHours) {
    //        this.workHours = workHours;
    //    }
    //
    //    @Override
    //    public void setVacationDays(VacationDays vacationDays) {
    //        this.vacationDays = vacationDays;
    //    }
    //
    //    @Override
    //    public void setPensionScheme(PensionScheme pensionScheme) {
    //        this.pensionScheme = pensionScheme;
    //    }
    //
    //    @Override
    //    public void setSalary(Salary salary) {
    //        this.salary = salary;
    //    }
    //
    //    @Override
    //    public void setTravelAllowance(TravelAllowance travelAllowance) {
    //        this.travelAllowance = travelAllowance;
    //    }
    //
    //    @Override
    //    public void setInsurance(Insurance insurance) {
    //        this.insurance = insurance;
    //    }

    @Override
    public Contract build() {
        return new Contract(nameEmployer, nameCandidate, addressCandidate, contractDuration, workHours, vacationDays,
                pensionScheme, salary, travelAllowance, insurance);
    }
}
