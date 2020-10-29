package designprojekt.demo.Repository;

import designprojekt.demo.Model.Expense;

import java.util.List;

public interface ExpenseRepository {

    int createExpenseTable(Expense expense, int id);
    List<Expense> expenseTable(Expense expense);
    Expense getExpenseById(int expenseId);
    Expense getAvgExpense();
}
