package designprojekt.demo.Repository.DatabaseHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHandler {

    private static DbHandler instance;
    private Connection connection;

    /**
     * @author Nicholas
     */


    private DbHandler(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306";
            String username = "test1234";
            String password = "test1234";
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public static synchronized DbHandler getInstance() throws SQLException{
        if (instance == null) {
            instance = new DbHandler();
        } else if (instance.getConnection().isClosed()){
            instance = new DbHandler();
        }
        return instance;
    }
}