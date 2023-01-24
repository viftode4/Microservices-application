package nl.tudelft.sem.template.user.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.user.integration.utils.JsonUtil;
import nl.tudelft.sem.template.user.models.RegisterCandidateModel;
import nl.tudelft.sem.template.user.models.RegistrationRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager", "mockRestTemplate"})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CandidateRegistrationTest {

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient JwtTokenVerifier mockJwtTokenVerifier;

    @Autowired
    private transient AuthManager mockAuthenticationManager;

    @Autowired
    private transient RestTemplate restTemplate;
    private transient MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    public void init() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void registerCandidate_worksCorrectly() throws Exception {

        // Arrange
        RegisterCandidateModel model = new RegisterCandidateModel();
        model.setAddress("Campus");
        model.setFirstName("John");
        model.setLastName("Smith");
        model.setNetId("jsmith");
        model.setPassword("password");
        String token = "Bearer testToken";

        RegistrationRequestModel registrationRequestModel =
            new RegistrationRequestModel("jsmith", "password");


        mockRestServiceServer.expect(ExpectedCount.once(),
            requestTo(new URI("http://localhost:8081/hr/registerCandidate")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(JsonUtil.serialize(registrationRequestModel)))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(JsonUtil.serialize(model)));

        when(mockJwtTokenVerifier.validateToken(any())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(any())).thenReturn("testUser");
        when(mockJwtTokenVerifier.getRoleFromToken(any())).thenReturn("HR");

        // Act
        ResultActions resultActions = mockMvc.perform(post("/registerCandidate")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", token)
            .content(JsonUtil.serialize(model)));

        //Assert

        MvcResult body = resultActions.andExpect(status().isOk()).andReturn();
        assertThat(body.getResponse().getContentAsString().equals("User jsmith created successfully"));
    }
}
