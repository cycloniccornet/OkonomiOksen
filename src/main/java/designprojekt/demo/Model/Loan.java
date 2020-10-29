package designprojekt.demo.Model;

/**
 * @Author Patrick Jønsson
 */

public class Loan {


    double loanAmount;
    double annualInterestRate;
    int yearOnLoan;
    double total;
    double monthlyExpense;
    int monthPayed;
    double k;
    double monthLeft;

    public Loan() {
    }


    public Loan(double loanAmount, double annualInterestRate, int yearOnLoan, double total, double monthlyExspenses, int monthPayed) {
        this.loanAmount = loanAmount;
        this.annualInterestRate = annualInterestRate;
        this.yearOnLoan = yearOnLoan;
        this.total = total;
        this.monthlyExpense = monthlyExspenses;
        this.monthPayed = monthPayed;
    }

    public Loan(double total, double monthlyExpense, int monthPayed) {
        this.total = total;
        this.monthlyExpense = monthlyExpense;
        this.monthPayed = monthPayed;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public int getYearOnLoan() {
        return yearOnLoan;
    }

    public void setYearOnLoan(int yearOnLoan) {
        this.yearOnLoan = yearOnLoan;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getMonthlyExpense() {
        return monthlyExpense;
    }

    public void setMonthlyExpense(double monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }

    public int getMonthPayed() {
        return monthPayed;
    }

    public void setMonthPayed(int monthPayed) {
        this.monthPayed = monthPayed;
    }

    public double getMonthLeft() {
        return monthLeft;
    }

    public void setMonthLeft(double monthLeft) {
        this.monthLeft = monthLeft;
    }
}
