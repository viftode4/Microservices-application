package nl.tudelft.sem.template.contract.domain.contract;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
// activate profiles to have spring use mocks during auto-injection of certain beans.
@ActiveProfiles({"test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ContractServiceTests {

    @Autowired
    private transient ContractService contractService;

    @Autowired
    private transient ContractRepository contractRepository;

    @Test
    public void contractMethods_withValidData_worksCorrectly() throws Exception {
        // Arrange
        final Name nameEmployer = new Name("Sem");
        final Name nameCandidate = new Name("Tommy");
        final Address addressCandidate = new Address("Leiden");
        final ContractDuration contractDuration = ContractDuration.PART_TIME;
        final WorkHours workHours = new WorkHours(10);
        final VacationDays vacationDays = new VacationDays(15);
        final PensionScheme pensionScheme = new PensionScheme("Scheme");
        final Salary salary = new Salary("Scale", "Steps");
        final TravelAllowance travelAllowance = new TravelAllowance(50.55);
        final Insurance insurance = new Insurance(true);

        Contract contract = new Contract(nameEmployer, nameCandidate, addressCandidate, contractDuration, workHours,
                vacationDays, pensionScheme, salary, travelAllowance, insurance);

        // Act
        contractService.addContract(nameEmployer, nameCandidate, addressCandidate, contractDuration, workHours, vacationDays,
                pensionScheme, salary, travelAllowance, insurance);

        // Assert
        Contract savedContract = contractRepository.findByNameCandidate(nameCandidate).orElseThrow();

        assertThat(contract.getNameCandidate()).isEqualTo(contractService.getContract(nameCandidate).getNameCandidate());
        assertThat(savedContract.getNameCandidate()).isEqualTo(nameCandidate);
        assertThat(savedContract.getNameEmployer()).isEqualTo(nameEmployer);
        assertThat(savedContract.getAddressCandidate()).isEqualTo(addressCandidate);
        assertThat(savedContract.getContractDuration()).isEqualTo(contractDuration);
        assertThat(savedContract.getWorkHours()).isEqualTo(workHours);
        assertThat(savedContract.getVacationDays()).isEqualTo(vacationDays);
        assertThat(savedContract.getPensionScheme()).isEqualTo(pensionScheme);
        assertThat(savedContract.getSalary()).isEqualTo(salary);
        assertThat(savedContract.getTravelAllowance()).isEqualTo(travelAllowance);
        assertThat(savedContract.getInsurance()).isEqualTo(insurance);

        contractService.finaliseContract(nameCandidate);

        savedContract = contractRepository.findByNameCandidate(nameCandidate).orElseThrow();
        assertThat(savedContract.getStatus()).isEqualTo(Status.FINAL);

        assertThat(contractRepository.existsByNameCandidate(nameCandidate)).isTrue();
        contractService.deleteContract(nameCandidate);
        assertThat(contractRepository.existsByNameCandidate(nameCandidate)).isFalse();
    }

    @Test
    public void createAndDeleteContract_withExistingUser_throwsException() {
        // Arrange
        final Name nameEmployer = new Name("Sem");
        final Name nameCandidate = new Name("Tommy");
        final Address addressCandidate = new Address("Leiden");
        final ContractDuration contractDuration = ContractDuration.PART_TIME;
        final WorkHours workHours = new WorkHours(10);
        final VacationDays vacationDays = new VacationDays(15);
        final PensionScheme pensionScheme = new PensionScheme("Scheme");
        final Salary salary = new Salary("Scale", "Steps");
        final TravelAllowance travelAllowance = new TravelAllowance(50.55);
        final Insurance insurance = new Insurance(true);
        final WorkHours newWorkHours = new WorkHours(20);

        Contract existingContract = new Contract(nameEmployer, nameCandidate, addressCandidate, contractDuration,
                workHours, vacationDays, pensionScheme, salary, travelAllowance, insurance);
        contractRepository.save(existingContract);

        // Act
        ThrowingCallable action = () -> contractService.addContract(nameEmployer, nameCandidate, addressCandidate,
                contractDuration, newWorkHours, vacationDays, pensionScheme, salary, travelAllowance, insurance);

        ThrowingCallable deleteAction = () -> contractService.deleteContract(new Name("test"));

        ThrowingCallable viewAction = () -> contractService.deleteContract(new Name("test"));

        ThrowingCallable finaliseAction = () -> contractService.finaliseContract(new Name("test"));

        // Assert
        assertThatExceptionOfType(Exception.class)
                .isThrownBy(action);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(deleteAction);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(viewAction);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(finaliseAction);

        Contract savedContract = contractRepository.findByNameCandidate(nameCandidate).orElseThrow();

        assertThat(savedContract.getNameCandidate()).isEqualTo(nameCandidate);
        assertThat(savedContract.getNameEmployer()).isEqualTo(nameEmployer);
        assertThat(savedContract.getAddressCandidate()).isEqualTo(addressCandidate);
        assertThat(savedContract.getContractDuration()).isEqualTo(contractDuration);
        assertThat(savedContract.getWorkHours()).isEqualTo(workHours);
        assertThat(savedContract.getVacationDays()).isEqualTo(vacationDays);
        assertThat(savedContract.getPensionScheme()).isEqualTo(pensionScheme);
        assertThat(savedContract.getSalary()).isEqualTo(salary);
        assertThat(savedContract.getTravelAllowance()).isEqualTo(travelAllowance);
        assertThat(savedContract.getInsurance()).isEqualTo(insurance);
    }
}
