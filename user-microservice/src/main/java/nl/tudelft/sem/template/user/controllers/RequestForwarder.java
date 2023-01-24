package nl.tudelft.sem.template.user.controllers;

import java.util.Map;
import nl.tudelft.sem.template.user.models.ContractFindRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class RequestForwarder {

    private final transient RestTemplate restTemplate;

    @Autowired
    public RequestForwarder(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void forwardToContractFinalise(String token, String candidateName) {
        HttpHeaders headers = new HttpHeaders(); // set the headers
        headers.setBearerAuth(token.split(" ")[1]);

        ContractFindRequestModel contractFindRequestModel = new ContractFindRequestModel(); // create a Model for the request
        contractFindRequestModel.setNameCandidate(candidateName);

        HttpEntity<ContractFindRequestModel> registrationRequestModel = new HttpEntity<>(contractFindRequestModel, headers);

        restTemplate.exchange("http://localhost:8083/request/finaliseContractRequest", HttpMethod.POST,
            registrationRequestModel, String.class );  //forward request
    }

    public ResponseEntity<String> forwardToContractProposeChange(String token, String message) {

        MultiValueMap<String, String> mvp = new LinkedMultiValueMap<>();
        mvp.setAll(Map.of("AUTHORIZATION", token, "CONTENT_TYPE", MediaType.TEXT_PLAIN.toString()));

        HttpEntity<String> httpEntity = new HttpEntity<>(message, new HttpHeaders(mvp));

        return restTemplate.exchange("http://localhost:8083/request/proposeChange", HttpMethod.POST,
            httpEntity, String.class );
    }

}
