package nl.tudelft.sem.template.user.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Optional;
import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.user.authentication.RoleManager;
import nl.tudelft.sem.template.user.domain.User;
import nl.tudelft.sem.template.user.domain.UserRepository;
import nl.tudelft.sem.template.user.domain.UserRole;
import nl.tudelft.sem.template.user.integration.utils.JsonUtil;
import nl.tudelft.sem.template.user.models.ContractFindRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@ExtendWith(SpringExtension.class)
// activate profiles to have spring use mocks during auto-injection of certain beans.
@ActiveProfiles(profiles = {"test", "mockUserRepository", "mockUserRepository", "mockTokenVerifier", "mockAuthenticationManager", "mockRoleManger", "mockRestTemplate"})
@AutoConfigureMockMvc
public class ContractControllerTest {
    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient JwtTokenVerifier mockJwtTokenVerifier;

    @Autowired
    private transient RoleManager mockRoleManager;

    @Autowired
    private transient UserRepository mockUserRepository;

    @Autowired
    private transient RestTemplate restTemplate;
    private transient MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    public void init() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void acceptContract_worksCorrectly() throws Exception {
        // Arrange
        String token = "Bearer test.Token.B";

        User user = new User();
        user.setAddress("Campus");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setNetId("testUser1");
        user.setUserRole(UserRole.Candidate);

        ContractFindRequestModel contractFindRequestModel = new ContractFindRequestModel();
        contractFindRequestModel.setNameCandidate("Test User");
        mockRestServiceServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8083/request/finaliseContractRequest")))
            .andExpect(method(HttpMethod.POST))
            .andExpect(content().json(JsonUtil.serialize(contractFindRequestModel)))
            .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
               );
        when(mockRoleManager.isCandidate()).thenReturn(true);
        when(mockRoleManager.getNetId()).thenReturn("testUser1");
        when(mockUserRepository.findByNetId("testUser1")).thenReturn(Optional.of(user));

        when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(any())).thenReturn("testUser1");
        when(mockJwtTokenVerifier.getRoleFromToken(any())).thenReturn("Candidate");

        // Act
        ResultActions resultActions = mockMvc.perform(post("/contract/agreeOnContract")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token));

        //Assert
        verify(mockRoleManager, times(1)).isCandidate();
        verify(mockRoleManager, times(1)).getNetId();

        MvcResult body = resultActions.andExpect(status().isOk()).andReturn();
        assertThat(body.getResponse().getContentAsString().equals("You have agreed to your contract!"));
    }

    @Test
    public void acceptContract_wrongRoleGivesError() throws Exception {
        // Arrange
        String token = "Bearer test.Token.B";

        User user = new User();
        user.setAddress("Campus");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setNetId("testUser1");
        user.setUserRole(UserRole.Employee);

        ContractFindRequestModel contractFindRequestModel = new ContractFindRequestModel();
        contractFindRequestModel.setNameCandidate("Test User");
        mockRestServiceServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8083/request/finaliseContractRequest")))
            .andExpect(method(HttpMethod.POST))
            .andExpect(content().json(JsonUtil.serialize(contractFindRequestModel)))
            .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
            );
        when(mockRoleManager.getRole()).thenReturn("Employee");
        when(mockRoleManager.isCandidate()).thenReturn(false);
        when(mockRoleManager.getNetId()).thenReturn("testUser1");
        when(mockUserRepository.findByNetId("testUser1")).thenReturn(Optional.of(user));

        when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(any())).thenReturn("testUser1");
        when(mockJwtTokenVerifier.getRoleFromToken(any())).thenReturn("Candidate");

        // Act
        ResultActions resultActions = mockMvc.perform(post("/contract/agreeOnContract")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token));

        //Assert

        MvcResult body = resultActions.andExpect(status().isIAmATeapot()).andReturn();
        assertThat(body.getResponse().getContentAsString()
            .equals("You are not a candidate, therefore, you do not have a pending contract to agree on!"));
    }

    @Test
    public void acceptContract_noContractGivesError() throws Exception {
        // Arrange
        String token = "Bearer test.Token.B";

        ContractFindRequestModel contractFindRequestModel = new ContractFindRequestModel();
        contractFindRequestModel.setNameCandidate("Test User");
        mockRestServiceServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8083/request/finaliseContractRequest")))
            .andExpect(method(HttpMethod.POST))
            .andExpect(content().json(JsonUtil.serialize(contractFindRequestModel)))
            .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
            );
        when(mockRoleManager.isCandidate()).thenReturn(true);
        when(mockRoleManager.getNetId()).thenReturn("testUser1");
        when(mockUserRepository.findByNetId("testUser1")).thenReturn(Optional.empty());

        when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(any())).thenReturn("testUser1");
        when(mockJwtTokenVerifier.getRoleFromToken(any())).thenReturn("Candidate");

        // Act
        ResultActions resultActions = mockMvc.perform(post("/contract/agreeOnContract")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token));

        //Assert

        MvcResult body = resultActions.andExpect(status().isNoContent()).andReturn();
        assertThat(body.getResponse().getContentAsString()
            .equals("Your user was not found in the database." +
                " Please ask your HR person to register you as a Candidate first!"));
    }
}
