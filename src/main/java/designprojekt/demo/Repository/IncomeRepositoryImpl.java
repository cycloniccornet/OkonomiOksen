package designprojekt.demo.Repository;

import designprojekt.demo.Model.Income;
import designprojekt.demo.Repository.DatabaseHandler.DbHandler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IncomeRepositoryImpl implements IncomeRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /*
    public Connection connect(){
        try {
            Connection connection = (Connection) DbHandler.getInstance();
            return connection;
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return null;

    }
     */

    /**
     *
     * @author Nicholas
     */
    @Override
    public int createIncomeTable(Income income, int userId) {
        int entryId = -1;
        log.info("Create income table");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement;
            if (userId == 0) {
                preparedStatement = connection.prepareStatement("INSERT INTO okonomi_oksen.income" +
                        "(salary, housingSubsidy, equities, others, total_income)" +
                        "VALUES (?,?,?,?,?)");

                preparedStatement.setDouble(1, income.getSalary());
                preparedStatement.setDouble(2, income.getHousingSubsidy());
                preparedStatement.setDouble(3, income.getEquities());
                preparedStatement.setDouble(4, income.getOthers());
                preparedStatement.setDouble(5, (income.getSalary()+income.getHousingSubsidy()+income.getEquities()+income.getOthers()));
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = connection.prepareStatement("INSERT INTO okonomi_oksen.income" +
                        "(salary, housingSubsidy, equities, others, total_income, user_fk_id)" +
                        "VALUES (?,?,?,?,?,?)");

                preparedStatement.setDouble(1, income.getSalary());
                preparedStatement.setDouble(2, income.getHousingSubsidy());
                preparedStatement.setDouble(3, income.getEquities());
                preparedStatement.setDouble(4, income.getOthers());
                preparedStatement.setDouble(5, (income.getSalary()+income.getHousingSubsidy()+income.getEquities()+income.getOthers()));
                preparedStatement.setInt(6, userId);
                preparedStatement.executeUpdate();
            }

            preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                entryId = resultSet.getInt(1);
            }
            connection.close();
            return entryId;

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return -1;
    }

    @Override
    public List<Income> incomeTable(Income income) {
        log.info("Get all income from database");

        List<Income> incomeList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM okonomi_oksen.income");

            while(resultSet.next()){
                Income incomes = new Income();
                income.setId(resultSet.getInt("income_id"));
                income.setSalary(resultSet.getLong("salary"));
                income.setHousingSubsidy(resultSet.getLong("housingSubsidy"));
                income.setEquities(resultSet.getLong("equities"));
                income.setOthers(resultSet.getLong("others"));
                incomeList.add(incomes);
            }
            connection.close();
            return incomeList;
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return null;
    }

    @Override
    public Income getIncomeById(int id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM okonomi_oksen.income WHERE income_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Income current = new Income();
                current.setId(resultSet.getInt("income_id"));
                current.setSalary(resultSet.getLong("salary"));
                current.setHousingSubsidy(resultSet.getLong("housingSubsidy"));
                current.setEquities(resultSet.getLong("equities"));
                current.setOthers(resultSet.getLong("others"));
                return current;
            }
            connection.close();

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return null;
    }

    public Income getLastIncomeByUserId(int userId) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM okonomi_oksen.income,okonomi_oksen.user WHERE user_fk_id = user_id AND user_id = ?");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Income> results = new ArrayList<>();

            while (resultSet.next()) {
                Income current = new Income();
                current.setId(resultSet.getInt("income_id"));
                current.setSalary(resultSet.getLong("salary"));
                current.setHousingSubsidy(resultSet.getLong("housingSubsidy"));
                current.setEquities(resultSet.getLong("equities"));
                current.setOthers(resultSet.getLong("others"));
                results.add(current);
            }
            connection.close();
            return results.get(results.size()-1);

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return null;
    }

    public Income getAvgIncome(){
        Income incomeAVG = new Income();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            Statement statement = connection.createStatement();
            ResultSet resultSetAVG = statement.executeQuery(
                    "SELECT AVG(salary) AS salary,\n" +
                            "AVG(housingSubsidy) AS housingSubsidy,\n" +
                            "AVG(equities) AS equities,\n" +
                            "AVG(others) AS others\n" +
                            "FROM okonomi_oksen.income");
            while (resultSetAVG.next()){
                incomeAVG.setSalary(resultSetAVG.getInt("salary"));
                incomeAVG.setHousingSubsidy(resultSetAVG.getInt("housingSubsidy"));
                incomeAVG.setEquities(resultSetAVG.getInt("equities"));
                incomeAVG.setOthers(resultSetAVG.getInt("others"));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return incomeAVG;
    }
}