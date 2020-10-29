package designprojekt.demo.Repository;

import designprojekt.demo.Model.User;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

@Repository
public class HashRepositoryImpl implements HashRepository {

    //prøv lige at lave den dynamisk dit dovne dyr
    private  String salt = "n0 H4Ck My p422W0rD";



    @Override
    public String hashPassword(String password) {

        if (password == null) {
            return null;
        }

        password = password + salt;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes(), 0, password.length());
            String hashedPassword = new BigInteger(1, md.digest()).toString(16);

            return hashedPassword;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean usernameExists(User user) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM okonomi_oksen.`user` WHERE username = ?");
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            User current = new User();

            while (resultSet.next()) {
                current.setUsername(resultSet.getString("username"));
                if (current.getUsername().equals(user.getUsername())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean passwordExists(User user) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM okonomi_oksen.`user` WHERE password = ?");
            preparedStatement.setString(1, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            User current = new User();

            while (resultSet.next()) {
                current.setPassword(resultSet.getString("password"));
                if (current.getPassword().equals(user.getPassword())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
