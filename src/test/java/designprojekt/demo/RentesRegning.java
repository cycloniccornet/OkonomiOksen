package designprojekt.demo;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;



public class RentesRegning {

    /**
     * @Author Patrick Jønsson
     * <p>Method that calculates the monthly sum to pay off a loan.
     * however, it assumes that interest rates are only credited once a year. </p>
     * @Param k0 The total sum of your loan
     * @Param R Yearly expenses in percentage
     * @Param n Amount of years on the loan
     * @Param k Total sum of money needed to be payed back after n years
     * @return Sum of money to pay every month for n years to pay of loan
     */
    @Test
    public void amountToPayBack(){
        //formel = K=k0 x (1 + R)^n

        int k0 = 500000;
        double R = 12;
        int n = 4;
        double k = k0* Math.pow((1.0 +R/100.0),n);

        double expectedResult = 16390.826;

        double result = k/(n*12);

        assertEquals(expectedResult, result, 0.5);
    }

    /**
     * @Author Patrick Jønsson
     * <p>Method that calculates how many month you have left untill a loan is payed off </p>
     * @Param monthlyPayment How much money is payed back each month
     * @Param timesPayed Amount of months already payed
     * @Param k Total sum of money needed to be payed back after n years
     * @return number of months left before loan is payed off
     */
    @Test
    public void howManyPaymentsLeft(){

        double monthlyPayment = 16390.826;
        double k = 786759.68;
        int timesPayed = 4;
        double expectedResult = 44;
        double result = (k/monthlyPayment) - timesPayed;

        assertEquals(expectedResult, result, 0.05);
    }


}
