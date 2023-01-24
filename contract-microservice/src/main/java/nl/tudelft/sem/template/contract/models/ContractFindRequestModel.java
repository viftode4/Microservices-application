package nl.tudelft.sem.template.contract.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractFindRequestModel {
    private String nameCandidate;
}
