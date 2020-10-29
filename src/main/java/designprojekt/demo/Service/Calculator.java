package designprojekt.demo.Service;

import designprojekt.demo.Model.Expense;
import designprojekt.demo.Model.Income;
import org.springframework.stereotype.Service;

@Service
public class Calculator {


    public long expenseSum(long residential, long regulars, long transport, long food, long miscellaneous){
        return residential+regulars+transport+food+miscellaneous;
    }

    public long sum(long value, long expenseSum){
        return  value-expenseSum;
    }

}
