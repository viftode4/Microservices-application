package nl.tudelft.sem.template.contract.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.contract.authentication.AuthManager;
import nl.tudelft.sem.template.contract.domain.contract.Address;
import nl.tudelft.sem.template.contract.domain.contract.Contract;
import nl.tudelft.sem.template.contract.domain.contract.ContractDuration;
import nl.tudelft.sem.template.contract.domain.contract.ContractService;
import nl.tudelft.sem.template.contract.domain.contract.Insurance;
import nl.tudelft.sem.template.contract.domain.contract.Name;
import nl.tudelft.sem.template.contract.domain.contract.PensionScheme;
import nl.tudelft.sem.template.contract.domain.contract.Salary;
import nl.tudelft.sem.template.contract.domain.contract.Status;
import nl.tudelft.sem.template.contract.domain.contract.TravelAllowance;
import nl.tudelft.sem.template.contract.domain.contract.VacationDays;
import nl.tudelft.sem.template.contract.domain.contract.WorkHours;
import nl.tudelft.sem.template.contract.models.ContractAddRequestModel;
import nl.tudelft.sem.template.contract.models.ContractFindRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RestController
public class ContractController {

    private final transient AuthManager authManager;
    private final transient ContractService contractService;

    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user
     */
    @Autowired
    public ContractController(AuthManager authManager, ContractService contractService) {
        this.authManager = authManager;
        this.contractService = contractService;
    }

    /**
     * Adds a contract to the database.
     *
     * @param request the contract model
     * @return 200 OK if the operation is successful
     * @throws Exception if a contract with the same candidate name already exists
     */
    @PostMapping("/contract/add")
    public ResponseEntity<String> contractAdd(@RequestBody ContractAddRequestModel request) throws Exception {
        if (!authManager.getRole().equals("HR")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized "
                    + "to add a contract to the database.");
        }
        try {
            contractService.addContract(new Name(request.getNameEmployer()),
                    new Name(request.getNameCandidate()), new Address(request.getAddressCandidate()),
                    ContractDuration.fromDuration(request.getContractDuration()), new WorkHours(request.getWorkHours()),
                    new VacationDays(request.getVacationDays()), new PensionScheme(request.getPensionScheme()),
                    new Salary(request.getSalaryScale(), request.getSalarySteps()),
                    new TravelAllowance(request.getTravelAllowance()), new Insurance(request.getInsurance()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok("Contract added successfully");
    }

    /**
     * Deletes a contract from the database.
     *
     * @param request the request model
     * @return 200 OK if the operation is successful
     * @throws Exception if no contract could be found to be deleted
     */
    @PostMapping("/contract/delete")
    public ResponseEntity<String> contractDelete(@RequestBody ContractFindRequestModel request) throws Exception {
        if (!authManager.getRole().equals("HR")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized "
                    + "to delete a contract.");
        }
        try {
            Name nameCandidate = new Name(request.getNameCandidate());
            contractService.deleteContract(nameCandidate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok("Contract deleted successfully");
    }

    /**
     * Deletes a contract from the database.
     *
     * @param request the request model
     * @return 200 OK if the operation is successful
     * @throws Exception if no contract could be found to be deleted
     */
    @PostMapping("/contract/modify")
    public ResponseEntity<String> contractModify(@RequestBody ContractAddRequestModel request) throws Exception {
        if (!authManager.getRole().equals("HR")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized "
                    + "to modify a contract.");
        }
        try {
            Status status = Status.valueOf(request.getStatus());
            Name nameEmployer = new Name(request.getNameEmployer());
            Name nameCandidate = new Name(request.getNameCandidate());
            Address addressCandidate = new Address(request.getAddressCandidate());
            ContractDuration contractDuration = ContractDuration.fromDuration(request.getContractDuration());
            WorkHours workHours = new WorkHours(request.getWorkHours());
            VacationDays vacationDays = new VacationDays(request.getVacationDays());
            PensionScheme pensionScheme = new PensionScheme(request.getPensionScheme());
            Salary salary = new Salary(request.getSalaryScale(), request.getSalarySteps());
            TravelAllowance travelAllowance = new TravelAllowance(request.getTravelAllowance());
            Insurance insurance = new Insurance(request.getInsurance());

            contractService.modifyContract(new Contract(nameEmployer, nameCandidate, addressCandidate, contractDuration, workHours,
                    vacationDays, pensionScheme, salary, travelAllowance, insurance), status);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok("Contract modified successfully");
    }

    /**
     * View a contract in the database.
     *
     * @return 200 OK if the operation is successful
     * @throws Exception if no contract could be found to be viewed
     */
    @GetMapping("/contract/view/{name}")
    public ResponseEntity<String> contractView(@PathVariable(value = "name") String name) throws Exception {
        try {
            Name nameCandidate = new Name(name);
            Contract contract = contractService.getContract(nameCandidate);

            return ResponseEntity.ok(contract.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    /**
     * Finalises a contract.
     *
     * @param request the request model
     * @return 200 OK if the operation is successful
     * @throws Exception if no contract could be found.
     */
    @PostMapping("/contract/finalise")
    public ResponseEntity<String> contractFinalise(@RequestBody ContractFindRequestModel request) throws Exception {
        try {
            contractService.finaliseContract(new Name(request.getNameCandidate()));
            return ResponseEntity.ok("Contract finalised successfully");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
