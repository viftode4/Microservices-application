package nl.tudelft.sem.template.user.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class UserTest {

    private static User testUser;

    @BeforeEach
    void createTestUser() {
        testUser = new User("John", "Smith", "TU Campus", "jsmith", UserRole.Employee);
    }


    @Test
    void getNetId() {
        assertEquals("jsmith", testUser.getNetId());
    }

    @Test
    void getFirstName() {
        assertEquals("John", testUser.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Smith", testUser.getLastName());
    }

    @Test
    void getAddress() {
        assertEquals("TU Campus", testUser.getAddress());
    }

    @Test
    void getUserRole() {
        assertEquals(UserRole.Employee, testUser.getUserRole());
    }


    @Test
    void setNetId() {
        testUser.setNetId("johnsmith");
        assertEquals("johnsmith", testUser.getNetId());
    }

    @Test
    void setFirstName() {
        testUser.setFirstName("Mark");
        assertEquals("Mark", testUser.getFirstName());
    }

    @Test
    void setLastName() {
        testUser.setLastName("Jones");
        assertEquals("Jones", testUser.getLastName());
    }

    @Test
    void setAddress() {
        testUser.setAddress("Den Haag");
        assertEquals("Den Haag", testUser.getAddress());
    }

    @Test
    void setUserRole() {
        testUser.setUserRole(UserRole.HR);
        assertEquals(UserRole.HR, testUser.getUserRole());
    }

    @Test
    void testEquals() {
        User other = new User("John", "Smith", "TU Campus", "jsmith", UserRole.Employee);
        assertThat(testUser.equals(other)).isTrue();
        other.setNetId("notTheSame");
        assertThat(testUser.equals(other)).isFalse();
    }

    @Test
    void toStringTest() {
        String expexted = "User "
            + "jsmith" + '\''
            + ", first name='" + "John" + '\''
            + ", last name='" + "Smith" + '\''
            + ", with role=" + "Employee";
        assertEquals(expexted, testUser.toString());
    }
}