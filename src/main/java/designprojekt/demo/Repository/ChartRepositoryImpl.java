package designprojekt.demo.Repository;

import designprojekt.demo.Model.Expense;
import designprojekt.demo.Model.Income;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.sql.*;

@Repository
public class ChartRepositoryImpl implements ChartRepository {

    @Override
    public void genPieChartExpense(int expenseId){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM okonomi_oksen.expense where expense_id = ?");
            preparedStatement.setInt(1, expenseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Expense expense = new Expense();
            while (resultSet.next()){
                expense.setResidential(resultSet.getInt("residential"));
                expense.setRegulars(resultSet.getInt("regulars"));
                expense.setTransport(resultSet.getInt("transport"));
                expense.setFood(resultSet.getInt("food"));
                expense.setMiscellaneous(resultSet.getInt("miscellaneous"));
                expense.setOverall_expenses(resultSet.getInt("overall_expenses"));
                expense.setLoan(resultSet.getInt("loan"));
            }

            DefaultPieDataset dataset = new DefaultPieDataset();
            dataset.setValue("Bolig", expense.getResidential());
            dataset.setValue("Øvrige Faste", expense.getRegulars());
            dataset.setValue("Transport", expense.getTransport());
            dataset.setValue("Mad", expense.getFood());
            dataset.setValue("Diverse", expense.getMiscellaneous());
            dataset.setValue("Lån", expense.getLoan());

            JFreeChart chart = ChartFactory.createPieChart("Udgifter", dataset, true, true, false);

            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setLegendLabelGenerator(
                    new StandardPieSectionLabelGenerator("{0} {2}"));
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));
            File file = new File("src/main/resources/static/images/PieChartExpense.png");
            ChartUtilities.saveChartAsPNG(file, chart, 500, 500);


        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    @Override
    public void genBarChartExpense(int expenseId){
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM okonomi_oksen.expense where expense_id = ?");
            preparedStatement.setInt(1, expenseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Expense expense = new Expense();
            while (resultSet.next()){
                expense.setResidential(resultSet.getLong("residential"));
                expense.setRegulars(resultSet.getInt("regulars"));
                expense.setTransport(resultSet.getInt("transport"));
                expense.setFood(resultSet.getInt("food"));
                expense.setMiscellaneous(resultSet.getInt("miscellaneous"));
                expense.setOverall_expenses(resultSet.getInt("overall_expenses"));
                expense.setLoan(resultSet.getInt("loan"));
            }

            Statement statement = connection.createStatement();
            ResultSet resultSetAVG = statement.executeQuery("SELECT AVG(regulars) AS regulars,\n" +
                    "AVG(residential) AS residential,\n" +
                    "AVG(transport) AS transport,\n" +
                    "AVG(food) AS food,\n" +
                    "AVG(miscellaneous) AS miscellaneous,\n" +
                    "AVG(overall_expenses) AS overall_expenses,\n" +
                    "AVG(loan) AS loan\n" +
                    "FROM okonomi_oksen.expense");

            Expense expenseAVG = new Expense();
            while (resultSetAVG.next()){
                expenseAVG.setResidential(resultSetAVG.getInt("residential"));
                expenseAVG.setRegulars(resultSetAVG.getInt("regulars"));
                expenseAVG.setTransport(resultSetAVG.getInt("transport"));
                expenseAVG.setFood(resultSetAVG.getInt("food"));
                expenseAVG.setMiscellaneous(resultSetAVG.getInt("miscellaneous"));
                expenseAVG.setOverall_expenses(resultSetAVG.getInt("overall_expenses"));
                expenseAVG.setLoan(resultSetAVG.getInt("loan"));
            }
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            dataset.addValue(expense.getResidential(), "bruger", "Bolig");
            dataset.addValue(expense.getRegulars(), "bruger", "Faste");
            dataset.addValue(expense.getTransport(), "bruger", "Transport");
            dataset.addValue(expense.getFood(), "bruger", "Mad");
            dataset.addValue(expense.getMiscellaneous(), "bruger", "Diverse");
            dataset.addValue(expense.getLoan(), "bruger", "Lån");


            dataset.addValue(expenseAVG.getResidential(), "gennemsnit", "Bolig");
            dataset.addValue(expenseAVG.getRegulars(), "gennemsnit", "Faste");
            dataset.addValue(expenseAVG.getTransport(), "gennemsnit", "Transport");
            dataset.addValue(expenseAVG.getFood(), "gennemsnit", "Mad");
            dataset.addValue(expenseAVG.getMiscellaneous(), "gennemsnit", "Diverse");
            dataset.addValue(expenseAVG.getLoan(), "gennemsnit", "Lån");



            JFreeChart chart = ChartFactory.createBarChart("Budget - Udgifter", "Kategori", "Dkk", dataset, PlotOrientation.VERTICAL,true,true,false);
            File file = new File("src/main/resources/static/images/BarChartExpense.png");
            ChartUtilities.saveChartAsPNG(file, chart, 500, 500);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    @Override
    public void genBarChartIncome(int incomeId){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM okonomi_oksen.income where income_id = ?");
            preparedStatement.setInt(1, incomeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Income income = new Income();
            while (resultSet.next()){
                income.setSalary(resultSet.getInt("salary"));
                income.setHousingSubsidy(resultSet.getInt("housingSubsidy"));
                income.setEquities(resultSet.getInt("equities"));
                income.setOthers(resultSet.getInt("others"));
            }

            Statement statement = connection.createStatement();
            ResultSet resultSetAVG = statement.executeQuery(
                    "SELECT AVG(salary) AS salary,\n" +
                    "AVG(housingSubsidy) AS housingSubsidy,\n" +
                    "AVG(equities) AS equities,\n" +
                    "AVG(others) AS others\n" +
                    "FROM okonomi_oksen.income");


            Income incomeAVG = new Income();
            while (resultSetAVG.next()){
                incomeAVG.setSalary(resultSetAVG.getInt("salary"));
                incomeAVG.setHousingSubsidy(resultSetAVG.getInt("housingSubsidy"));
                incomeAVG.setEquities(resultSetAVG.getInt("equities"));
                incomeAVG.setOthers(resultSetAVG.getInt("others"));
            }
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            dataset.addValue(income.getSalary(), "bruger", "Løn");
            dataset.addValue(income.getHousingSubsidy(), "bruger", "Bolig støtte");
            dataset.addValue(income.getEquities(), "bruger", "Likvider");
            dataset.addValue(income.getOthers(), "bruger", "Andet");


            dataset.addValue(incomeAVG.getSalary(), "gennemsnit", "Løn");
            dataset.addValue(incomeAVG.getHousingSubsidy(), "gennemsnit", "Bolig støtte");
            dataset.addValue(incomeAVG.getEquities(), "gennemsnit", "Likvider");
            dataset.addValue(incomeAVG.getOthers(), "gennemsnit", "Andet");



            JFreeChart chart = ChartFactory.createBarChart("Budget - Indtægter", "Kategori", "Dkk", dataset, PlotOrientation.VERTICAL,true,true,false);
            File file = new File("src/main/resources/static/images/BarChartIncome.png");
            ChartUtilities.saveChartAsPNG(file, chart, 500, 500);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }


    @Override
    public void genPieChartIncome(int incomeId){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "test1234", "test1234");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM okonomi_oksen.income where income_id = ?");
            preparedStatement.setInt(1, incomeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Income income = new Income();
            while (resultSet.next()){
                income.setSalary(resultSet.getInt("salary"));
                income.setHousingSubsidy(resultSet.getInt("housingSubsidy"));
                income.setEquities(resultSet.getInt("equities"));
                income.setOthers(resultSet.getInt("others"));
            }

            DefaultPieDataset dataset = new DefaultPieDataset();
            dataset.setValue("Løn", income.getSalary());
            dataset.setValue("Boligstøtte", income.getHousingSubsidy());
            dataset.setValue("Aktier", income.getEquities());
            dataset.setValue("Øvrige", income.getOthers());

            JFreeChart chart = ChartFactory.createPieChart("Indtægter", dataset, true, true, false);

            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setLegendLabelGenerator(
                    new StandardPieSectionLabelGenerator("{0} {2}"));
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));
            File file = new File("src/main/resources/static/images/PieChartIncome.png");
            ChartUtilities.saveChartAsPNG(file, chart, 500, 500);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }
}