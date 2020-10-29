package designprojekt.demo.Repository;

import designprojekt.demo.Model.User;

public interface HashRepository {


    String hashPassword(String password);

    boolean usernameExists(User user);

    boolean passwordExists(User user);
}
