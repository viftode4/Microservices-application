package nl.tudelft.sem.template.requests.domain.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RequestServiceTests {

    private transient RequestService requestService;
    private RequestsRepository requestsRepository;

    @BeforeEach
    void setUp() {
        requestsRepository = mock(RequestsRepository.class);
        requestService = new RequestService(requestsRepository);
    }

    //test AddRequest method with a view request successfully and verify that the request is added and is as expected
    @Test
    void testAddRequestSuccess() {
        when(requestsRepository.existsById(1)).thenReturn(false);
        Requests expected = new Requests(1, RequestType.VIEW);

        Requests request = requestService.addRequest(1, RequestType.VIEW);
        verify(requestsRepository, times(1)).save(request);
        assertEquals(expected, request);
    }

    //test AddRequest method with a view request unsuccessfully and verify that the request is not added
    @Test
    void testAddRequestFail() {
        when(requestsRepository.existsById(1)).thenReturn(true);
        Requests expected = new Requests(1, RequestType.VIEW);

        Requests request = requestService.addRequest(1, RequestType.VIEW);
        verify(requestsRepository, times(0)).save(request);

        assertNotEquals(expected, request);
        assertEquals(null, request);
    }

    //test deleteRequest method with a view request successfully and verify that the request is deleted and is as expected
    @Test
    void testDeleteRequestSuccess() throws Exception {
        Requests expected = new Requests(1, RequestType.VIEW);
        when(requestsRepository.findById(1)).thenReturn(java.util.Optional.of(expected));
        Requests request = requestService.deleteRequest(1);
        verify(requestsRepository, times(1)).delete(request);
        assertEquals(expected, request);
    }


    //test deleteRequest method with a view request unsuccessfully and
    //verify that the request is not deleted and it throws an exception
    @Test
    void testDeleteRequestFailException(){
        when(requestsRepository.findById(1)).thenReturn(java.util.Optional.empty());
        assertThrows(Exception.class, () -> requestService.deleteRequest(1));
    }

    //test checkRequestIsUnique method with a view request successfully and verify that the request is unique
    @Test
    void testCheckRequestIsUniqueSuccess() {
        when(requestsRepository.existsById(1)).thenReturn(false);
        boolean request = requestService.checkRequestIsUnique(1);
        verify(requestsRepository, times(1)).existsById(1);
        assertEquals(true, request);
    }

    //test checkRequestIsUnique method with a view request unsuccessfully and verify that the request is not unique
    @Test
    void testCheckRequestIsUniqueFail() {
        when(requestsRepository.existsById(1)).thenReturn(true);
        boolean request = requestService.checkRequestIsUnique(1);
        verify(requestsRepository, times(1)).existsById(1);
        assertEquals(false, request);
    }


    //write deleteRequest tests
    //write checkRequestIsUnique tests

}
