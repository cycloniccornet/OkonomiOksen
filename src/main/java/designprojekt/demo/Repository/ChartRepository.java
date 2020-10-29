package designprojekt.demo.Repository;

public interface ChartRepository {

    void genPieChartExpense(int expenseId);

    void genBarChartExpense(int expenseId);

    void genBarChartIncome(int incomeId);

    void genPieChartIncome(int incomeId);
}
