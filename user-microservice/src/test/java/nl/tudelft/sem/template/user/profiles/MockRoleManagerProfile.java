package nl.tudelft.sem.template.user.profiles;

import nl.tudelft.sem.template.user.authentication.RoleManager;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("mockRoleManger")
@Configuration
public class MockRoleManagerProfile {
    /**
     * Mocks the RoleManager.
     *
     * @return A mocked RoleManager.
     */
    @Bean
    @Primary  // marks this bean as the first bean to use when trying to inject an RoleManager
    public RoleManager getMockitoROleManger() {
        return Mockito.mock(RoleManager.class);
    }
}

