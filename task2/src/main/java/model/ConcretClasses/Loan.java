package model.ConcretClasses;

public class Loan {
    private double amount;
    private boolean collateralRequired;
    private double rate;
    private int termInDays;
    private String type;
    private String due;
    private String status;
    private String id;
    private double repaymentAmount;

    public Loan(double amount, boolean collateralRequired, double rate, int termInDays, String type){
        this.amount = amount;
        this.collateralRequired = collateralRequired;
        this.rate = rate;
        this.termInDays = termInDays;
        this.type = type;
    }

    public Loan(String due, String id, double repaymentAmount, String status, String type) {
        this.due = due;
        this.id = id;
        this.repaymentAmount = repaymentAmount;
        this.status = status;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isCollateralRequired() {
        return collateralRequired;
    }

    public double getRate() {
        return rate;
    }

    public int getTermInDays() {
        return termInDays;
    }

    public String getType() {
        return type;
    }

    public String toString(){
        String s = "amount: " + amount + " collateralRequired: " + collateralRequired + " rate: " + rate + " termInDays: " + termInDays + " type: " + type;
        return s;
    }

    public String getDue() {
        return due;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public double getRepaymentAmount() {
        return repaymentAmount;
    }
}
