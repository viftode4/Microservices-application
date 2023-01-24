package nl.tudelft.sem.template.user.profiles;

import nl.tudelft.sem.template.user.domain.UserRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("mockUserRepository")
@Configuration
public class MockUserRepositoryProfile {

    /**
     * Mocks the UserRepository.
     *
     * @return A mocked UserRepository.
     */
    @Bean
    @Primary  // marks this bean as the first bean to use when trying to inject an UserRepository
    public UserRepository getMockUserRepository() {
        return Mockito.mock(UserRepository.class);
    }
}
