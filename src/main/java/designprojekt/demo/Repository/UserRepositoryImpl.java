package designprojekt.demo.Repository;

import designprojekt.demo.Model.User;
import designprojekt.demo.Repository.DatabaseHandler.DbHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HashRepository hashRepository;


    @Override
    public int createUser(User user) {
        int userId = -1;
        log.info("Creating new User");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO okonomi_oksen.user (username, password) VALUES (?,?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, hashRepository.hashPassword(user.getPassword()));
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt(1);
            }
            connection.close();
            return userId;
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return 0;
    }

    @Override
    public User findUserById(int userId) {
        log.info("Finding User By Id");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM okonomi_oksen.user WHERE user_id = ?");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return null;
    }

    @Override
    public User checkLogin(String username, String password) {
        log.info("Checking User login credentials");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM okonomi_oksen.user WHERE user.username = ? AND user.password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashRepository.hashPassword(password));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                connection.close();
                return user;
            }
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return null;
    }

    @Override
    public int deleteUser(int userId) {
        log.info("Deleting user");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM okonomi_oksen.user WHERE user_id = ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            connection.close();

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return 0;
    }


}