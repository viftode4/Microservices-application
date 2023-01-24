package nl.tudelft.sem.template.requests.controllers;

import lombok.Generated;
import nl.tudelft.sem.template.requests.models.ContractAddRequestModel;
import nl.tudelft.sem.template.requests.models.ContractFindRequestModel;
import nl.tudelft.sem.template.requests.models.NotificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Generated
@RestController
public class RequestsController {

    private final transient String auth = "Authorization";
    private final transient RestTemplate restTemplate;

    @Autowired
    public RequestsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Sets the headers, given the mediaType and token.
     *
     * @param mediaType
     * @param token
     * @return headers, an HttpHeaders which can be used in sending the requests
     */
    public HttpHeaders setHeaders(MediaType mediaType, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setBearerAuth(token.split(" ")[1]);
        return headers;
    }

    /**
     * Sends the request to the contract microservice to create the contract.
     *
     * @param request The request model
     * @param token The jwt token
     * @return 200 OK if the operation is successful
     * @throws Exception if the request fails
     */
    @Generated
    @PostMapping("/request/createContractRequest")
    public ResponseEntity<String> createContractRequest(@RequestBody ContractAddRequestModel request,
                                      @RequestHeader(auth) String token) throws Exception {
        try {
            HttpEntity<ContractAddRequestModel> entity = new HttpEntity<>(request, setHeaders(MediaType.APPLICATION_JSON, token));
            ResponseEntity<String> response = new RestTemplate().postForEntity("http://localhost:8084/contract/add", entity, String.class);
            sendNotification(response.toString().split(",")[1], token);
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Tells the contract microservice to terminate a contract.
     *
     * @param request The request model
     * @param token The jwt token
     * @return 200 OK if the operation is successful
     * @throws Exception if the request fails
     */
    @Generated
    @PostMapping("/request/terminateContractRequest")
    public ResponseEntity<String> terminateContractRequest(@RequestBody ContractFindRequestModel request,
                                         @RequestHeader(auth) String token) throws Exception {
        try {
            HttpEntity<ContractFindRequestModel> entity = new HttpEntity<>(request, setHeaders(MediaType.APPLICATION_JSON, token));
            ResponseEntity<String> response = new RestTemplate().postForEntity("http://localhost:8084/contract/delete", entity, String.class);
            sendNotification(response.toString().split(",")[1], token);
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Sends the changes to the contract microservice which then makes the changes.
     *
     * @param request The request model
     * @param token The jwt token
     * @return 200 OK if the operation is successful
     * @throws Exception if the request fails
     */
    @Generated
    @PostMapping("/request/modifyContractRequest")
    public ResponseEntity<String> modifyContractRequest(@RequestBody ContractAddRequestModel request,
                                      @RequestHeader(auth) String token) throws Exception {
        try {
            HttpEntity<ContractAddRequestModel> entity = new HttpEntity<>(request, setHeaders(MediaType.APPLICATION_JSON, token));
            ResponseEntity<String> response = new RestTemplate().postForEntity("http://localhost:8084/contract/modify", entity, String.class);
            sendNotification(response.toString().split(",")[1], token);
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Sends a request to contract to view the contract, then it should return the user the contract.
     *
     * @param token The jwt token
     * @return 200 OK if the operation is successful
     * @throws Exception if the request fails
     */
    @Generated
    @GetMapping("/request/viewContractRequest/{name}")
    public ResponseEntity<String> viewContractRequest(@PathVariable(value = "name") String name,
                                    @RequestHeader(auth) String token) throws Exception {
        try {
            HttpEntity<ContractFindRequestModel> entity = new HttpEntity<>(setHeaders(MediaType.APPLICATION_JSON, token));
            return new RestTemplate().exchange("http://localhost:8084/contract/view/" + name, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Sends a request to the contract microservice to finalise a contract.
     *
     * @param request The request model
     * @param token The jwt token
     * @return 200 OK if the operation is successful
     * @throws Exception if the request fails
     */
    @Generated
    @PostMapping("/request/finaliseContractRequest")
    public ResponseEntity<String> finaliseContractRequest(@RequestBody ContractFindRequestModel request,
                                                         @RequestHeader(auth) String token) throws Exception {
        try {
            HttpEntity<ContractFindRequestModel> entity = new HttpEntity<>(request, setHeaders(MediaType.APPLICATION_JSON, token));
            ResponseEntity<String> response = new RestTemplate().postForEntity("http://localhost:8084/contract/finalise", entity, String.class);
            sendNotification(response.toString().split(",")[1], token);
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Send a request to the request microservice to propose a contract change.
     *
     * @param token The jwt token
     * @param changeString The content of the proposal
     * @return 200 OK if the operation is successful
     * @throws Exception if the request fails
     */
    @PostMapping("request/proposeChange")
    public ResponseEntity<String> proposeChange(@RequestHeader("Authorization") String token,
                                                @RequestBody String changeString) throws Exception {
        try {
            sendNotification(changeString, token);
            return ResponseEntity.ok("Succesful proposed contract change");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }



    /**
     * Sends a notification to all HR users.
     *
     * @param content The content of the notification
     * @param token The jwt token
     * @throws Exception If it fails
     */
    @Generated
    public void sendNotification(String content, String token) throws Exception {
        try {
            String users = new RestTemplate().getForObject("http://localhost:8081/hr/getAllHr", String.class);
            for (String user : users.split(",")) {
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                NotificationModel model = new NotificationModel();
                model.setMessage(content);
                model.setNetId(user);
                HttpEntity<NotificationModel> entity = new HttpEntity<>(model, headers);
                new RestTemplate().postForEntity("http://localhost:8089/createNotification", entity, String.class);
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
