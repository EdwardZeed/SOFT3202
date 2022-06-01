package model.POJOs;

/**
 * A Plain Old Java Object (POJO) that represents the conversion rate.
 * This class records the conversion rate between two currencies.
 */
public final class Rate {
    private double rate;
    private String startCurrency;
    private String endCurrency;

    /**
     * Instantiates a new Rate.
     *
     * @param startCurrency the start currency
     * @param endCurrency   the end currency
     * @param rate          the rate
     */
    public Rate(String startCurrency, String endCurrency, double rate) {
        this.rate = rate;
        this.startCurrency = startCurrency;
        this.endCurrency = endCurrency;
    }

    /**
     * Gets rate.
     *
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * Gets from country.
     *
     * @return the start currency code
     */
    public String getFrom() {
        return startCurrency;
    }

    /**
     * Gets to country.
     *
     * @return the end currency code
     */
    public String getTo() {
        return endCurrency;
    }
}
