package nl.tudelft.sem.template.contract.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nl.tudelft.sem.template.contract.authentication.AuthManager;
import nl.tudelft.sem.template.contract.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.contract.domain.contract.*;
import nl.tudelft.sem.template.contract.integration.utils.JsonUtil;
import nl.tudelft.sem.template.contract.models.ContractAddRequestModel;
import nl.tudelft.sem.template.contract.models.ContractFindRequestModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@ExtendWith(SpringExtension.class)
// activate profiles to have spring use mocks during auto-injection of certain beans.
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager"})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ContractsTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private transient JwtTokenVerifier mockJwtTokenVerifier;

    @Autowired
    private transient AuthManager mockAuthenticationManager;

    @Autowired
    private transient ContractRepository contractRepository;

    @Test
    public void contractMethods_withValidData_worksCorrectly() throws Exception {
        when(mockAuthenticationManager.getNetId()).thenReturn("ExampleUser");
        when(mockAuthenticationManager.getRole()).thenReturn("HR");
        when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");
        when(mockJwtTokenVerifier.getRoleFromToken(anyString())).thenReturn("HR");

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

        ContractAddRequestModel model = new ContractAddRequestModel(
                Status.FINAL.toString(),
                nameEmployer.toString(),
                nameCandidate.toString(),
                addressCandidate.toString(),
                contractDuration.toString(),
                workHours.getHours(),
                vacationDays.getDays(),
                pensionScheme.toString(),
                salary.getScale(),
                salary.getSteps(),
                travelAllowance.getAllowance(),
                insurance.getHasInsurance()
        );

        // Act
        ResultActions resultActions = mockMvc.perform(post("/contract/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer MockedToken")
                .content(JsonUtil.serialize(model)));

        // Assert
        resultActions.andExpect(status().isOk());

        ContractFindRequestModel viewModel = new ContractFindRequestModel("Tommy");

        // Act
        ResultActions viewActions = mockMvc.perform(get("/contract/view/Tommy")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer MockedToken"));

        // Assert
        viewActions.andExpect(status().isOk());

        ResultActions finaliseActions = mockMvc.perform(post("/contract/finalise")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer MockedToken")
                .content(JsonUtil.serialize(viewModel)));

        // Assert
        finaliseActions.andExpect(status().isOk());

        ResultActions modifyActions = mockMvc.perform(post("/contract/modify")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer MockedToken")
                .content(JsonUtil.serialize(model)));

        // Assert
        modifyActions.andExpect(status().isOk());

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

        ContractFindRequestModel deleteModel = new ContractFindRequestModel("Tommy");
        assertThat(contractRepository.existsByNameCandidate(nameCandidate)).isTrue();

        ResultActions deleteActions = mockMvc.perform(post("/contract/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer MockedToken")
                .content(JsonUtil.serialize(deleteModel)));

        deleteActions.andExpect(status().isOk());
        assertThat(contractRepository.existsByNameCandidate(nameCandidate)).isFalse();

    }

    @Test
    public void contractAddAndDelete_withoutAuthorization_throwsException() throws Exception {
        when(mockAuthenticationManager.getNetId()).thenReturn("ExampleUser");
        when(mockAuthenticationManager.getRole()).thenReturn("EMPLOYEE");
        when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");
        when(mockJwtTokenVerifier.getRoleFromToken(anyString())).thenReturn("EMPLOYEE");

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

        ContractAddRequestModel model = new ContractAddRequestModel(
                Status.FINAL.toString(),
                nameEmployer.toString(),
                nameCandidate.toString(),
                addressCandidate.toString(),
                contractDuration.toString(),
                workHours.getHours(),
                vacationDays.getDays(),
                pensionScheme.toString(),
                salary.getScale(),
                salary.getSteps(),
                travelAllowance.getAllowance(),
                insurance.getHasInsurance()
        );

        // Act
        ResultActions resultActions = mockMvc.perform(post("/contract/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer MockedToken")
                .content(JsonUtil.serialize(model)));

        // Assert
        resultActions.andExpect(status().is4xxClientError());

        ContractFindRequestModel deleteModel = new ContractFindRequestModel("Tommy");

        ResultActions deleteActions = mockMvc.perform(post("/contract/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer MockedToken")
                .content(JsonUtil.serialize(deleteModel)));

        deleteActions.andExpect(status().is4xxClientError());
    }
}
