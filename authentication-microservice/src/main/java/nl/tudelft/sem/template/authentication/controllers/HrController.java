package nl.tudelft.sem.template.authentication.controllers;

import nl.tudelft.sem.template.authentication.authentication.JwtTokenGenerator;
import nl.tudelft.sem.template.authentication.authentication.JwtUserDetailsService;
import nl.tudelft.sem.template.authentication.authentication.RoleManager;
import nl.tudelft.sem.template.authentication.domain.user.*;
import nl.tudelft.sem.template.authentication.models.AuthenticationRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/hr")
public class HrController {
    private final transient AuthenticationManager authenticationManager;

    private final transient JwtTokenGenerator jwtTokenGenerator;

    private final transient JwtUserDetailsService jwtUserDetailsService;

    private final transient RegistrationService registrationService;

    private final transient RoleManager roleManager;

    private final transient UserRepository userRepository;

    /**
     * Instantiates a new HrController.
     *
     * @param authenticationManager the authentication manager
     * @param jwtTokenGenerator     the token generator
     * @param jwtUserDetailsService the user service
     * @param registrationService   the registration service
     */
    public HrController(AuthenticationManager authenticationManager,
                        JwtTokenGenerator jwtTokenGenerator,
                        JwtUserDetailsService jwtUserDetailsService,
                        RegistrationService registrationService,
                        RoleManager roleManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.registrationService = registrationService;
        this.roleManager = roleManager;
        this.userRepository = userRepository;
    }

    /**
     * Register a new user.
     *
     * @param request the registration request model
     * @return the response entity
     */
    @PostMapping("/registerCandidate")
    public ResponseEntity registerCandidate(@RequestBody AuthenticationRequestModel request) throws Exception {
        if(roleManager.getRole().equals("HR")) {
            try {
                NetId netId = new NetId(request.getNetId());
                Password password = new Password(request.getPassword());
                registrationService.registerCandidate(netId, password);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized " +
                    "to create a new employee account");
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Set the HR role to a user.
     *
     * @param request the registration request model
     * @return the response entity
     */
    @PostMapping("/setHr")
    public ResponseEntity setHr(@RequestBody AuthenticationRequestModel request) throws Exception {
        try {
            NetId netId = new NetId(request.getNetId());
            registrationService.setHr(netId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("getAllHr")
    public ResponseEntity<String> getAllHr() throws Exception {
        try {
            return ResponseEntity.ok(registrationService.findAllHR());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
