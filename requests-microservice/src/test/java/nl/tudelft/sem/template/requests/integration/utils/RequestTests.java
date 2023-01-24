package nl.tudelft.sem.template.requests.integration.utils;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.sem.template.requests.authentication.AuthManager;
import nl.tudelft.sem.template.requests.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.requests.domain.requests.RequestType;
import nl.tudelft.sem.template.requests.domain.requests.Requests;
import nl.tudelft.sem.template.requests.domain.requests.RequestsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith(SpringExtension.class)
// activate profiles to have spring use mocks during auto-injection of certain beans.
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager"})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RequestTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private transient JwtTokenVerifier mockJwtTokenVerifier;

    @Autowired
    private transient AuthManager mockAuthenticationManager;

    @Autowired
    private transient RequestsRepository requestsRepository;

    private static Requests testRequest;

    @BeforeEach
    void createRequest() {
        testRequest = new Requests(1, RequestType.VIEW);
    }

    @Test
    void getIdTest() {
        assertEquals(1, testRequest.getid());
    }

    @Test
    void getRequestTypeTest() {
        assertEquals(RequestType.VIEW, testRequest.getRequestType());
    }


}
