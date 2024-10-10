package dev.earlspilner.users.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Alexander Dudkin
 */
@SpringBootTest
@ActiveProfiles("hsqldb")
class UserServiceSpringDataJpaTests extends AbstractUserServiceTests {

}
