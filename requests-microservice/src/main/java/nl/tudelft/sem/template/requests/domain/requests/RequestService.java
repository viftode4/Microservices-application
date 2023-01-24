package nl.tudelft.sem.template.requests.domain.requests;

import org.springframework.stereotype.Service;


/**
 * Service for adding new requests.
 */
@Service
public class RequestService {

    private final transient RequestsRepository requestsRepository;

    /**
     * Creates a new RequestService.
     *
     * @param requestsRepository the request repository
     */
    public RequestService(RequestsRepository requestsRepository) {
        this.requestsRepository = requestsRepository;
    }

    /**
     * Adds a new request to the request database.
     *
     * @param id request id
     * @param requestType request type
     * @return returns the request that was added, if the request exists it returns null
     */
    public Requests addRequest(int id, RequestType requestType) {
        if (checkRequestIsUnique(id)) {
            Requests request = new Requests(id, requestType);
            requestsRepository.save(request);
            return request;
        }
        return null;
    }

    /**
     * deletes a request from the database.
     *
     * @param id id of request that is to be deleted
     * @return the deleted request
     * @throws Exception if the request does not exist
     */
    public Requests deleteRequest(int id) throws Exception {

        Requests request = requestsRepository.findById(id).orElseThrow();
        requestsRepository.delete(request);

        return request;
    }



    /**
     * Checks if the request is unique using id.
     *
     * @param id id for unique request
     * @return true if request is unique false if not
     */
    public boolean checkRequestIsUnique(int id) {
        return !requestsRepository.existsById(id);
    }
}