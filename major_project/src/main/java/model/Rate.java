package model;

public final class Rate {
    private double rate;
    private String startCurrency;
    private String endCurrency;

    public Rate(String startCurrency, String endCurrency, double rate) {
        this.rate = rate;
        this.startCurrency = startCurrency;
        this.endCurrency = endCurrency;
    }

    public double getRate() {
        return rate;
    }

    public String getFrom() {
        return startCurrency;
    }

    public String getTo() {
        return endCurrency;
    }
}
