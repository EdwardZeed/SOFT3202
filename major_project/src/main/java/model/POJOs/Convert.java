package model.POJOs;

/**
 * A Plain Old Java Object (POJO) that represents a conversion.
 * This class records the start currency and the end currency,the result of the conversion.
 */
public final class Convert {
    private double result;
    private String from;
    private String to;

    /**
     * Instantiates a new Convert.
     *
     * @param result the result of the conversion
     * @param from   the start currency code
     * @param to     the end currency code
     */
    public Convert(double result, String from, String to) {
        this.result = result;
        this.from = from;
        this.to = to;
    }

    /**
     * Gets result of the conversion.
     *
     * @return the result of the conversion
     */
    public double getResult() {
        return result;
    }

    /**
     * Gets from country.
     *
     * @return the start currency code
     */
    public String getFrom() {
        return from;
    }

    /**
     * Gets to country.
     *
     * @return the end currency code
     */
    public String getTo() {
        return to;
    }
}
