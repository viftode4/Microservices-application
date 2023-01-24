package nl.tudelft.sem.template.authentication.domain.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

    //test user equals
    @Test
    void testEquals() {
        AppUser user1 = new AppUser(new NetId("netid"), new HashedPassword("password"));
        AppUser user2 = new AppUser(new NetId("netid"), new HashedPassword("password"));
        assertEquals(user1, user2);
    }

    //test change password
    @Test
    void testChangePassword() {
        AppUser user1 = new AppUser(new NetId("netid"), new HashedPassword("password"));
        user1.changePassword(new HashedPassword("password2"));
        assertEquals(user1.getPassword(), new HashedPassword("password2"));
    }

    //test AppUser constructor with role
    @Test
    void testAppUserWithRole() {
        AppUser user1 = new AppUser(new NetId("netid"), new HashedPassword("password"), Role.NONE);
        assertEquals(user1.getRole(), Role.NONE);
    }
}