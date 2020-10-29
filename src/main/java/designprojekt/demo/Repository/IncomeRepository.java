package designprojekt.demo.Repository;

import designprojekt.demo.Model.Income;

import java.util.List;

public interface IncomeRepository {

    int createIncomeTable(Income income, int userId);
    List<Income> incomeTable(Income income);
    Income getIncomeById(int id);
    Income getLastIncomeByUserId(int userId);
    Income getAvgIncome();
}
