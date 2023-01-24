package nl.tudelft.sem.template.requests.models;

import lombok.Data;
import nl.tudelft.sem.template.requests.domain.requests.RequestType;

@Data
public class RequestsAddRequestModel {
    private int id;
    private RequestType requestType;
}
