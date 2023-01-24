package nl.tudelft.sem.template.contract.domain.contract;

public interface Builder {
    void setAttributes(Name nameEmployer,
                 Name nameCandidate,
                 Address addressCandidate,
                 ContractDuration contractDuration,
                 WorkHours workHours,
                 VacationDays vacationDays,
                 PensionScheme pensionScheme,
                 Salary salary,
                 TravelAllowance travelAllowance,
                 Insurance insurance);

    //    void setStatus(Status status);
    //
    //    void setNameEmployer(Name nameEmployer);
    //
    //    void setNameCandidate(Name nameCandidate);
    //
    //    void setAddressCandidate(Address addressCandidate);
    //
    //    void setContractDuration(ContractDuration contractDuration);
    //
    //    void setWorkHours(WorkHours workHours);
    //
    //    void setVacationDays(VacationDays vacationDays);
    //
    //    void setPensionScheme(PensionScheme pensionScheme);
    //
    //    void setSalary(Salary salary);
    //
    //    void setTravelAllowance(TravelAllowance travelAllowance);
    //
    //    void setInsurance(Insurance insurance);

    Contract build();
}
