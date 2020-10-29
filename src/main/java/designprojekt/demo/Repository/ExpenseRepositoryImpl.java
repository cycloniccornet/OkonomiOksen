package designprojekt.demo.Repository;

import designprojekt.demo.Model.Expense;
import designprojekt.demo.Repository.DatabaseHandler.DbHandler;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {

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

    public void getOverallExpense(int expenseId){
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE okonomi_oksen.expense " +
                    "SET overall_expenses = (SELECT okonomi_oksen.expense.residential + " +
                    "okonomi_oksen.expense.regulars + okonomi_oksen.expense.transport + " +
                    "okonomi_oksen.expense.food + okonomi_oksen.expense.miscellaneous + okonomi_oksen.expense.loan) " +
                    "WHERE okonomi_oksen.expense.expense_id = ?");

            preparedStatement.setLong(1, expenseId);
            preparedStatement.executeQuery();

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    @Override
    public int createExpenseTable(Expense expense, int id) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO okonomi_oksen.expense (residential, regulars, transport, food, miscellaneous, loan, overall_expenses, income_fk_id) VALUES (?,?,?,?,?,?,?,?)");
            preparedStatement.setLong(1, expense.getResidential());
            preparedStatement.setLong(2, expense.getRegulars());
            preparedStatement.setLong(3, expense.getTransport());
            preparedStatement.setLong(4, expense.getFood());
            preparedStatement.setLong(5, expense.getMiscellaneous());
            preparedStatement.setLong(6, expense.getLoan());
            preparedStatement.setLong(7, expense.getResidential()+expense.getRegulars()+expense.getTransport()+expense.getFood()+expense.getMiscellaneous()+expense.getLoan());
            preparedStatement.setLong(8, id);

            preparedStatement.execute();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Expense> expenseTable(Expense expense){
        List<Expense> expenseList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from okonomi_oksen.expense");

            while(resultSet.next()){
                Expense expenses = new Expense();
                expense.setResidential(resultSet.getInt("residential"));
                expense.setRegulars(resultSet.getInt("regulars"));
                expense.setTransport(resultSet.getInt("transport"));
                expense.setFood(resultSet.getInt("food"));
                expense.setMiscellaneous(resultSet.getInt("miscellaneous"));
                expense.setLoan(resultSet.getInt("loan"));
                expense.setOverall_expenses(resultSet.getInt("overall_expenses"));
                expenseList.add(expenses);
            }
            connection.close();

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        return expenseList;
    }

    @Override
    public Expense getExpenseById(int expenseId){

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM okonomi_oksen.expense WHERE expense_id = ?");
            preparedStatement.setInt(1, expenseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Expense expense = new Expense();
                expense.setResidential(resultSet.getInt("residential"));
                expense.setRegulars(resultSet.getInt("regulars"));
                expense.setTransport(resultSet.getInt("transport"));
                expense.setFood(resultSet.getInt("food"));
                expense.setMiscellaneous(resultSet.getInt("miscellaneous"));
                expense.setLoan(resultSet.getInt("loan"));
                expense.setOverall_expenses(resultSet.getInt("overall_expenses"));
                return expense;
            }
            connection.close();

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return null;
    }

    public Expense getAvgExpense() {
        Expense expenseAVG = new Expense();
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            Statement statement = connection.createStatement();
            ResultSet resultSetAVG = statement.executeQuery("SELECT AVG(regulars) AS regulars,\n" +
                    "AVG(residential) AS residential,\n" +
                    "AVG(transport) AS transport,\n" +
                    "AVG(food) AS food,\n" +
                    "AVG(miscellaneous) AS miscellaneous,\n" +
                    "AVG(overall_expenses) AS overall_expenses,\n" +
                    "AVG(loan) AS loan\n" +
                    "FROM okonomi_oksen.expense");
            while (resultSetAVG.next()) {
                expenseAVG.setResidential(resultSetAVG.getInt("residential"));
                expenseAVG.setRegulars(resultSetAVG.getInt("regulars"));
                expenseAVG.setTransport(resultSetAVG.getInt("transport"));
                expenseAVG.setFood(resultSetAVG.getInt("food"));
                expenseAVG.setMiscellaneous(resultSetAVG.getInt("miscellaneous"));
                expenseAVG.setOverall_expenses(resultSetAVG.getInt("overall_expenses"));
                expenseAVG.setLoan(resultSetAVG.getInt("loan"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return expenseAVG;
    }
}