package nl.tudelft.sem.template.requests.domain.requests;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
public class Requests {

    /**
     * Identifier for Request.
     */
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @Convert(converter = IdAttributeConverter.class)
    private Integer id;

    @Column(name = "requestType", nullable = false)
    @Convert(converter = RequestTypeAttributeConverter.class)
    private RequestType requestType;


    /**
     * Request constructor.
     *
     * @param id unique request id
     * @param requestType request type
     */
    public Requests(int id, RequestType requestType) {
        this.id = id;
        this.requestType = requestType;
    }

    public int getid() {
        return id;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}