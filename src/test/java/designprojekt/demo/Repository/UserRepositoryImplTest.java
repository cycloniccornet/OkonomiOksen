package designprojekt.demo.Repository;

import designprojekt.demo.Model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class UserRepositoryImplTest {


    @Test
    void createUser() {
        UserRepository userRepository = new UserRepositoryImpl();
        assertEquals(2,userRepository.createUser(new User("Preben", "Preben")));
    }

    @Test
    void findUserById() {
    }

    @Test
    void checkLogin() {
    }
}