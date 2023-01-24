package nl.tudelft.sem.template.authentication.profiles;

import nl.tudelft.sem.template.authentication.authentication.RoleManager;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("mockRoleManager")
@Configuration
public class MockRoleManagerProfile {

    @Bean
    @Primary
    public RoleManager getMockRoleManager() {
        return Mockito.mock(RoleManager.class);
    }
}
