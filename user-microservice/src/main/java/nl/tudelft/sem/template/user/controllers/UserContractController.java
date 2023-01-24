package nl.tudelft.sem.template.user.controllers;

import java.util.Optional;
import nl.tudelft.sem.template.user.authentication.RoleManager;
import nl.tudelft.sem.template.user.domain.User;
import nl.tudelft.sem.template.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/contract")
public class UserContractController {

    private final transient RoleManager roleManager;
    private final transient UserRepository userRepository;
    private final transient RequestForwarder requestForwarder;

    @Autowired
    public UserContractController(RoleManager roleManager, UserRepository userRepository,
                                  RequestForwarder requestForwarder) {
        this.roleManager = roleManager;
        this.userRepository = userRepository;
        this.requestForwarder = requestForwarder;
    }

    /**
     * Returns the name of the user given the netId.
     *
     * @param netId the netId of the user to be found
     * @return The name of the candidate as a String
     * @throws ResponseStatusException If user is not found in the database
     */
    private String getCandidateName(String netId) throws ResponseStatusException {
        Optional<User> candidate = userRepository.findByNetId(netId);
        if(candidate.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Your user was not found in the database. " +
                "Please ask your HR person to register you as a Candidate first!");
        return candidate.get().getFullName();
    }

    @PostMapping("/agreeOnContract")
    private ResponseEntity<String> agreeOnContract(@RequestHeader("Authorization") String token) {
        try {
            if(!roleManager.isCandidate()) {
                throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT,
                    "You are not a candidate, therefore, you do not have a pending contract to agree on!");
            }

            String candidateName = getCandidateName(roleManager.getNetId());  // Get the candidate name from the netId

            requestForwarder.forwardToContractFinalise(token, candidateName);  //Forward request to the contract microservice
        }
        catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("You have agreed to your contract!");
    }

    @PostMapping("/proposeChange")
    private ResponseEntity<String> proposeChange(@RequestHeader("Authorization") String token,
                                                 @RequestBody String changeString) {
        try {
            String message = roleManager.getNetId() + " wants: " + changeString;

            return requestForwarder.forwardToContractProposeChange(token, message);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}
