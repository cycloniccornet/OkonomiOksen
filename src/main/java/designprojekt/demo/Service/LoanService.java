package designprojekt.demo.Service;

import designprojekt.demo.Controller.HomeController;
import org.springframework.stereotype.Service;

@Service
public class LoanService implements LoanServiceInterface {

    /**
     * <p>Method that calculates the monthly sum to pay off a loan.
     * however, it assumes that interest rates are only credited once a year. </p>
     * @Param k0 The total sum of your loan
     * @Param R Annual interest rate
     * @Param n Amount of years on the loan
     * @Param k Total sum of money needed to be payed back after n years
     * @return Montly exspenses
     */
    @Override
    public double amountToPayBack(int k0, double R, int n){
        double k = k0* Math.pow((1.0 +R/100.0), n);

        double result = k/(n*12);


        return result;
    }

    /**
     * <p>Method that calculates how many month you have left untill a loan is payed off </p>
     * @Param monthlyPayment How much money is payed back each month
     * @Param timesPayed Amount of months already payed
     * @Param k Total sum of money needed to be payed back after n years
     * @return number of months left before loan is payed off
     */
    @Override
    public double howManyPaymentsLeft(double k, double monthlyPayment, int timesPayed){

        double result = (k/monthlyPayment) - timesPayed;

        return result;
    }



}
