package nl.tudelft.sem.template.contract.domain.contract;

import java.util.Objects;
import javax.persistence.*;
import lombok.NoArgsConstructor;

/**
 * A DDD entity representing a contract in our domain.
 */
@Entity
@Table(name = "contracts")
@NoArgsConstructor
public class Contract {

    /**
     * Identifier for the contract.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status", nullable = false)
    @Convert(converter = StatusAttributeConverter.class)
    private Status status;

    @Column(name = "name_employer", nullable = false)
    @Convert(converter = NameAttributeConverter.class)
    private Name nameEmployer;

    @Column(name = "name_candidate", nullable = false, unique = true)
    @Convert(converter = NameAttributeConverter.class)
    private Name nameCandidate;

    @Column(name = "address_candidate", nullable = false)
    @Convert(converter = AddressAttributeConverter.class)
    private Address addressCandidate;

    @Column(name = "contract_duration", nullable = false)
    @Convert(converter = ContractDurationAttributeConverter.class)
    private ContractDuration contractDuration;

    @Column(name = "work_hours", nullable = false)
    @Convert(converter = WorkHoursAttributeConverter.class)
    private WorkHours workHours;

    @Column(name = "vacation_days", nullable = false)
    @Convert(converter = VacationDaysAttributeConverter.class)
    private VacationDays vacationDays;

    @Column(name = "pension_scheme", nullable = false)
    @Convert(converter = PensionSchemeAttributeConverter.class)
    private PensionScheme pensionScheme;

    @Column(name = "salary", nullable = false)
    @Convert(converter = SalaryAttributeConverter.class)
    private Salary salary;

    @Column(name = "travel_allowance", nullable = false)
    @Convert(converter = TravelAllowanceAttributeConverter.class)
    private TravelAllowance travelAllowance;

    @Column(name = "insurance", nullable = false)
    @Convert(converter = InsuranceAttributeConverter.class)
    private Insurance insurance;

    /**
     * Creates a contract.
     *
     * @param nameEmployer The name of the employer
     * @param nameCandidate The name of the candidate
     * @param addressCandidate The address of the candidate
     */
    public Contract(Name nameEmployer, Name nameCandidate, Address addressCandidate, ContractDuration contractDuration,
                    WorkHours workHours, VacationDays vacationDays, PensionScheme pensionScheme, Salary salary,
                    TravelAllowance travelAllowance, Insurance insurance) {
        this.status = Status.DRAFT;
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

    public void finalise() { this.status = Status.FINAL; }

    public Status getStatus() { return status; }

    public Name getNameEmployer() { return nameEmployer; }

    public Name getNameCandidate() { return nameCandidate; }

    public Address getAddressCandidate() { return addressCandidate; }

    public ContractDuration getContractDuration() { return contractDuration; }

    public WorkHours getWorkHours() { return workHours; }

    public VacationDays getVacationDays() { return vacationDays; }

    public PensionScheme getPensionScheme() { return pensionScheme; }

    public Salary getSalary() { return salary; }

    public TravelAllowance getTravelAllowance() { return travelAllowance; }

    public Insurance getInsurance() { return insurance; }

    @Override
    public String toString() {
        return "{\nStatus: " + status.toString()
                + ",\nNameEmployer: " + nameEmployer.toString()
                + ",\nNameCandidate: " + nameCandidate.toString()
                + ",\nAddressCandidate: " + addressCandidate.toString()
                + ",\nContractDuration: " + contractDuration.getDuration()
                + ",\nWorkHours: " + workHours.getHours()
                + ",\nVacationDays: " + vacationDays.getDays()
                + ",\nPensionScheme: " + pensionScheme.toString()
                + ",\nSalaryScale: " + salary.getScale()
                + ",\nSalarySteps: " + salary.getSteps()
                + ",\nTravelAllowance: " + travelAllowance.getAllowance()
                + ",\nInsurance: " + insurance.toString() + "\n}";
    }

    /**
     * Equality is only based on the name of the candidate.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) { return false; }
        return (this == o) || nameCandidate.equals(((Contract) o).getNameCandidate());
    }

    @Override
    public int hashCode() { return Objects.hash(nameCandidate); }
}
