package nl.tudelft.sem.template.user.controllers;

import lombok.Generated;
import nl.tudelft.sem.template.user.authentication.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Generated
@RestController
public class GetContractController {



    private final transient RoleManager roleManager;
    private final transient RestTemplate restTemplate;


    @Autowired
    public GetContractController(RoleManager roleManager, RestTemplate restTemplate) {
        this.roleManager = roleManager;
        this.restTemplate = restTemplate;
    }

    // make a get request to the requests microservice to get the contract of this user
    @GetMapping("/getContract")
    public ResponseEntity<String> getContract(@RequestHeader("Authorization") String token) {
        if(roleManager.getRole().equals("HR")) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT,
                "You are not a candidate, therefore, you do not have a contract in the application!");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.split(" ")[1]);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> contract = restTemplate.exchange("http://localhost:8083/request/viewContractRequest/" + roleManager.getNetId(),
                HttpMethod.GET,
            entity,
            String.class
        );
        return ResponseEntity.ok(contract.getBody());
    }






}
