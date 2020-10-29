package designprojekt.demo.Service;

import designprojekt.demo.Model.Expense;
import designprojekt.demo.Model.Income;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    public void sumAddUpExpenses(){
        Calculator cal = new Calculator();
        assertEquals(5, cal.expenseSum(1,1,1,1,1));
    }

    @Test
    public void sumAddUpAll(){
        Income income = new Income();
        income.setSalary(6);
        Calculator cal = new Calculator();
        assertEquals(1, cal.sum(6, cal.expenseSum(1,1,1,1,1)));
    }

}