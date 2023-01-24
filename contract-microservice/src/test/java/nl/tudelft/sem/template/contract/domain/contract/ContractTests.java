package nl.tudelft.sem.template.contract.domain.contract;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ContractTests {

    final Name nameEmployer = new Name("Sem");
    final Name nameCandidate = new Name("Tommy");
    final Address addressCandidate = new Address("Leiden");
    final ContractDuration contractDuration1 = ContractDuration.PART_TIME;
    final ContractDuration contractDuration2 = ContractDuration.PERMANENT;
    final WorkHours workHours = new WorkHours(10);
    final VacationDays vacationDays = new VacationDays(15);
    final PensionScheme pensionScheme = new PensionScheme("Scheme");
    final Salary salary = new Salary("Scale", "Steps");
    final TravelAllowance travelAllowance = new TravelAllowance(50.55);
    final Insurance insurance = new Insurance(true);
    Contract contract1 = new Contract(nameEmployer, nameCandidate, addressCandidate, contractDuration1,
            workHours, vacationDays, pensionScheme, salary, travelAllowance, insurance);
    Contract contract2 = new Contract(nameEmployer, nameCandidate, addressCandidate, contractDuration1,
            workHours, vacationDays, pensionScheme, salary, travelAllowance, insurance);
    Contract contract3 = new Contract(nameEmployer, nameCandidate, addressCandidate, contractDuration2,
            workHours, vacationDays, pensionScheme, salary, travelAllowance, insurance);
    Contract contract4 = new Contract(nameEmployer, new Name("Not"), addressCandidate, contractDuration1,
            workHours, vacationDays, pensionScheme, salary, travelAllowance, insurance);

    @Test
    void testEquals() {
        assertEquals(contract1, contract2);
        assertNotEquals(contract3, contract4);
        assertEquals(contract1.getNameEmployer(), nameEmployer);
        assertNotEquals(contract1, null);
        assertNotEquals(contract1, 1);
        assertEquals(contract1, contract1);
        contract1.finalise();
    }

    @Test
    void testNameEquals() {
        Name name1 = new Name("1");
        Name name2 = new Name("2");
        Name name3 = new Name("1");
        assertEquals(name1, name3);
        assertNotEquals(name1, name2);
        assertNotEquals(name1, 1);
        assertNotEquals(name1, null);
    }

    @Test
    void testContractDurationFromDurationMethod(){
        assertEquals(ContractDuration.fromDuration("PERMANENT"), ContractDuration.PERMANENT);
        assertEquals(ContractDuration.fromDuration("PART_TIME"), ContractDuration.PART_TIME);
        assertEquals(ContractDuration.fromDuration("TEMPORARY"), ContractDuration.TEMPORARY);
        assertThrows(IllegalArgumentException.class, () -> ContractDuration.fromDuration("NOT"));
    }

}
