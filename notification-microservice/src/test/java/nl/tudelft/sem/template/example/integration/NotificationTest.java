package nl.tudelft.sem.template.example.integration;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@ExtendWith(SpringExtension.class)
// activate profiles to have spring use mocks during auto-injection of certain beans.
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class NotificationTest {

    @Autowired
    private MockMvc mockMvc;

    // test putting a notification in the database
    @Test
    public void testPutNotification() throws Exception {
        // Arrange
        String netId = "netId";
        String message = "message";
        String body = "{\"netId\": \"" + netId + "\", \"message\": \"" + message + "\"}";

        // Act
        ResultActions result = mockMvc.perform(post("/createNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        // Assert
        result.andExpect(status().isOk());
        result.andExpect(result1 -> assertThat(result1.getResponse().getContentAsString()).isEqualTo("Notification created!"));
    }

    // test getting a notification from the database and has the same message as the one put in
    @Test
    public void testGetNotification() throws Exception {
        // Arrange
        String netId = "netId";
        String message = "message";
        String body = "{\"netId\": \"" + netId + "\", \"message\": \"" + message + "\"}";

        // Act
        ResultActions result = mockMvc.perform(post("/createNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));

        result = mockMvc.perform(get("/getNotifications/" + netId));

        // Assert
        result.andExpect(status().isOk());
        assertThat(result.andReturn().getResponse().getContentAsString()).contains(message);
    }

    // test getting a notification after it has been deleted
    @Test
    public void testGetNotificationAfterDelete() throws Exception {
        // Arrange
        String netId = "netId";
        String message = "message";
        String body = "{\"netId\": \"" + netId + "\", \"message\": \"" + message + "\"}";

        // Act
        ResultActions result = mockMvc.perform(post("/createNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));

        result = mockMvc.perform(get("/getNotifications/" + netId));

        result = mockMvc.perform(get("/getNotifications/" + netId));

        // Assert
        result.andExpect(status().isOk());
        assertThat(result.andReturn().getResponse().getContentAsString()).isEqualTo("[]");
    }

    //test bad request from createNotification
    @Test
    public void testBadRequestCreateNotification() throws Exception {
        // Arrange
        String netId = "netId";
        String message = "message";
        String body = "{\"netId\": \"" + netId + "\"}";

        // Act
        ResultActions result = mockMvc.perform(post("/createNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        // Assert
        result.andExpect(status().isBadRequest());

        //get result body and assert it contains the error message
        assertThat(result.andReturn().getResponse().getContentAsString()).contains("Notification not created");
    }
}
