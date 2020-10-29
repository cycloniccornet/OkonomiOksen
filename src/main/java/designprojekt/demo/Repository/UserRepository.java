package designprojekt.demo.Repository;

import designprojekt.demo.Model.User;

public interface UserRepository {
    int createUser(User user);
    User findUserById(int userId);
    User checkLogin(String username, String password);
    int deleteUser(int userId);
}
