package nl.tudelft.sem.template.contract.domain.contract;

import org.springframework.stereotype.Service;

/**
 * A DDD service for adding new contracts.
 */
@Service
public class ContractService {

    private final transient ContractRepository contractRepository;

    /**
     * Instantiates a new ContractService.
     *
     * @param contractRepository the contract repository
     */
    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    /**
     * Adds a contract to the database.
     *
     * @param nameEmployer the name of the employer
     * @param nameCandidate the name of the candidate
     * @param addressCandidate the address of the candidate
     * @param contractDuration the duration of the contract
     * @param workHours the amount of work hours per week
     * @param vacationDays the amount of vacation days
     * @param pensionScheme the pension scheme
     * @param salary the salary details
     * @param travelAllowance the travel allowance
     * @param insurance the insurance
     * @return the contract that has been added
     * @throws Exception if a contract with the same candidate name already exists
     */
    public Contract addContract(Name nameEmployer, Name nameCandidate, Address addressCandidate,
                                ContractDuration contractDuration, WorkHours workHours, VacationDays vacationDays,
                                PensionScheme pensionScheme, Salary salary, TravelAllowance travelAllowance,
                                Insurance insurance) throws Exception {

        if (checkCandidateIsUnique(nameCandidate)) {

            // Create new account
            Builder builder = new ContractBuilder();

            Director director = new Director(builder);
            director.constructCustomContract(nameEmployer, nameCandidate, addressCandidate, contractDuration, workHours,
                    vacationDays, pensionScheme, salary, travelAllowance, insurance);


            Contract contract = builder.build();
            contractRepository.save(contract);

            return contract;
        } else {
            throw new CandidateAlreadyInUseException(nameCandidate);
        }

    }

    /**
     * Deletes a contract from the database.
     *
     * @param nameCandidate the name of the contract to be deleted
     * @return the deleted contract
     * @throws Exception if no such contract exists
     */
    public Contract deleteContract(Name nameCandidate) throws Exception {
        Contract contract = contractRepository.findByNameCandidate(nameCandidate).orElseThrow();
        contractRepository.delete(contract);

        return contract;
    }

    public boolean checkCandidateIsUnique(Name nameCandidate) {
        return !contractRepository.existsByNameCandidate(nameCandidate);
    }

    /**
     * Gets a contract from the database.
     *
     * @param nameCandidate the name of the candidate of the contract
     * @return the contract
     * @throws Exception if no such contract exists
     */
    public Contract getContract(Name nameCandidate) throws Exception {
        return contractRepository.findByNameCandidate(nameCandidate).orElseThrow();
    }

    /**
     * Modifies a contract.
     *
     * @param status the status of the contract
     * @param newContract the contract to be modified
     * @throws Exception if no contract exists or the name of the new contract does not match
     */
    public void modifyContract(Contract newContract, Status status) throws Exception {
        Contract oldContract = contractRepository.findByNameCandidate(newContract.getNameCandidate()).orElseThrow();
        if (!newContract.getNameCandidate().equals(oldContract.getNameCandidate())) {
            throw new Exception();
        }
        if (status.equals(Status.FINAL)) {
            newContract.finalise();
        }
        contractRepository.delete(oldContract);
        contractRepository.save(newContract);
    }

    /**
     * Finalises a contract.
     *
     * @param nameCandidate The name of the candidate of the contract
     * @throws Exception if no such contract exists
     */
    public void finaliseContract(Name nameCandidate) throws Exception {
        Contract contract = contractRepository.findByNameCandidate(nameCandidate).orElseThrow();
        contract.finalise();
        contractRepository.save(contract);
    }
}
