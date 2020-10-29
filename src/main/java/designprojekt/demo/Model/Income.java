package designprojekt.demo.Model;

public class Income {

    private int id;
    private long salary;
    private long housingSubsidy;
    private long equities;
    private long others;

    public Income() {
    }

    public Income(long salary, long housingSubsidy, long equities, long others) {
        this.salary = salary;
        this.housingSubsidy = housingSubsidy;
        this.equities = equities;
        this.others = others;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public long getHousingSubsidy() {
        return housingSubsidy;
    }

    public void setHousingSubsidy(long housingSubsidy) {
        this.housingSubsidy = housingSubsidy;
    }

    public long getEquities() {
        return equities;
    }

    public void setEquities(long equities) {
        this.equities = equities;
    }

    public long getOthers() {
        return others;
    }

    public void setOthers(long others) {
        this.others = others;
    }
}
