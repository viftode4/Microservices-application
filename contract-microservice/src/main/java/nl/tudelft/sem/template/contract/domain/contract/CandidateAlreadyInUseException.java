package nl.tudelft.sem.template.contract.domain.contract;

public class CandidateAlreadyInUseException extends Exception {
    static final long serialVersionUID = -4487516993124229948L;

    public CandidateAlreadyInUseException(Name nameCandidate) {
        super(nameCandidate.toString());
    }
}
