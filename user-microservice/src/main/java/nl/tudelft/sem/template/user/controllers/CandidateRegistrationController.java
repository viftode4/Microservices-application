package nl.tudelft.sem.template.user.controllers;

import java.util.ArrayList;
import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.domain.CandidateRegistrationService;
import nl.tudelft.sem.template.user.models.RegisterCandidateModel;
import nl.tudelft.sem.template.user.models.RegisterContractModel;
import nl.tudelft.sem.template.user.models.RegistrationRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;



/**
 * Controller used to register a new candidate.
 */
@RestController
public class CandidateRegistrationController {

    private final transient CandidateRegistrationService candidateRegistrationService;
    private final transient AuthManager authManager;
    private final transient RestTemplate restTemplate;

    /**
     * Constructor for Controller.
     *
     * @param candidateRegistrationService needed for auto-wiring
     * @param authManager needed for auto-wiring
     * @param restTemplate needed for auto-wiring
     */
    @Autowired
    public CandidateRegistrationController(
        CandidateRegistrationService candidateRegistrationService,
        AuthManager authManager, RestTemplate restTemplate) {
        this.candidateRegistrationService = candidateRegistrationService;
        this.authManager = authManager;
        this.restTemplate = restTemplate;
    }

    /**
     * Endpoint that takes the details of a candidate
     * and registers it within the authentication microservice.
     *
     * @param request - the details of the candidate
     *
     * @return Confirmation of creation
     */
    @PostMapping("/registerCandidate")
    public ResponseEntity<String> registerCandidate(@RequestBody RegisterCandidateModel request,
                                                     @RequestHeader("Authorization") String token) {
        try {
            restTemplate.exchange("http://localhost:8081/hr/registerCandidate", HttpMethod.POST, createHttpEntity(token, request), String.class);
            candidateRegistrationService.registerCandidate(getNamesAndAddress(request).get(0),
                    getNamesAndAddress(request).get(1), getNamesAndAddress(request).get(2), request.getNetId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("User " + request.getNetId() + " created successfully");
    }

    private HttpEntity<RegistrationRequestModel> createHttpEntity(String token, RegisterCandidateModel request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.split(" ")[1]);

        HttpEntity<RegistrationRequestModel> registrationRequestModel =
                new HttpEntity<>(new RegistrationRequestModel(request.getNetId(), request.getPassword()), headers);
        return registrationRequestModel;
    }

    private ArrayList<String> getNamesAndAddress(RegisterCandidateModel request) {
        ArrayList<String> result = new ArrayList<>();
        result.add(request.getFirstName());
        result.add(request.getLastName());
        result.add(request.getAddress());
        return result;
    }


}
