package designprojekt.demo.Model;

public class Expense {

    private long residential;
    private long regulars;
    private long transport;
    private long food;
    private long miscellaneous;
    private long overall_expenses;
    private long loan;

    public Expense() {
    }

    public Expense(long residential, long regulars, long transport, long food, long miscellaneous, long loan) {
        this.residential = residential;
        this.regulars = regulars;
        this.transport = transport;
        this.food = food;
        this.miscellaneous = miscellaneous;
        this.loan = loan;
    }

    public long getResidential() {
        return residential;
    }

    public void setResidential(long residential) {
        this.residential = residential;
    }

    public long getRegulars() {
        return regulars;
    }

    public void setRegulars(long regulars) {
        this.regulars = regulars;
    }

    public long getTransport() {
        return transport;
    }

    public void setTransport(long transport) {
        this.transport = transport;
    }

    public long getFood() {
        return food;
    }

    public void setFood(long food) {
        this.food = food;
    }

    public long getMiscellaneous() {
        return miscellaneous;
    }

    public void setMiscellaneous(long miscellaneous) {
        this.miscellaneous = miscellaneous;
    }

    public long getOverall_expenses() {
        return overall_expenses;
    }

    public void setOverall_expenses(long overall_expenses) {
        this.overall_expenses = overall_expenses;
    }

    public long getLoan() {
        return loan;
    }

    public void setLoan(long loan) {
        this.loan = loan;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "residential=" + residential +
                ", regulars=" + regulars +
                ", transport=" + transport +
                ", food=" + food +
                ", miscellaneous=" + miscellaneous +
                ", overall_expenses=" + overall_expenses +
                '}';
    }
}
