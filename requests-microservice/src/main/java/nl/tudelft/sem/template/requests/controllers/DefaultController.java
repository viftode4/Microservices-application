package nl.tudelft.sem.template.requests.controllers;

import nl.tudelft.sem.template.requests.authentication.AuthManager;
import nl.tudelft.sem.template.requests.domain.requests.RequestService;
import nl.tudelft.sem.template.requests.domain.requests.RequestType;
import nl.tudelft.sem.template.requests.models.RequestFindRequestModel;
import nl.tudelft.sem.template.requests.models.RequestsAddRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RestController
public class DefaultController {

    private final transient AuthManager authManager;
    private final transient RequestService requestService;


    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user.
     */
    @Autowired
    public DefaultController(AuthManager authManager, RequestService requestService) {
        this.authManager = authManager;
        this.requestService = requestService;
    }

    /**
     * Gets example by id.
     *
     * @return the example found in the database with the given id.
     */
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello " + authManager.getNetId());
    }

    /**
     * Adds a request to the database.
     *
     * @param request the request model.
     *
     * @return 200 OK if the operatuon is successful.
     * @throws Exception if a request with the same id exists.
     */
    @PostMapping("/request/addViewRequest")
    public ResponseEntity addViewRequest(@RequestBody RequestsAddRequestModel request) throws Exception {

        try {
            int id = request.getId();
            requestService.addRequest(id, RequestType.VIEW);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Edit an existing request.
     *
     * @param request the request model
     * @return 200 OK if the operation is successful
     * @throws Exception if a request with the same id exists
     */
    @PostMapping("/request/addEditRequest")
    public ResponseEntity addEditRequest(@RequestBody RequestsAddRequestModel request) throws Exception {

        try {
            int id = request.getId();
            requestService.addRequest(id, RequestType.EDIT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a request from the database.
     */
    @PostMapping("/request/deleteRequest")
    public ResponseEntity requestDelete(@RequestBody RequestFindRequestModel request) throws Exception {

        try {
            int id = request.getId();
            requestService.deleteRequest(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

}
