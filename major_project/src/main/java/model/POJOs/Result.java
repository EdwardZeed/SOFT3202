package model.POJOs;

/**
 * A Plain Old Java Object (POJO) that represents the conversation with API.
 * This class records the rate and convert result from the API.
 */
public final class Result {
    private Rate rate;
    private Convert convertedResult;

    /**
     * Instantiates a new Result.
     *
     * @param rate            the rate
     * @param convertedResult the converted result
     */
    public Result(Rate rate, Convert convertedResult) {
        this.rate = rate;
        this.convertedResult = convertedResult;
    }

    /**
     * Gets rate.
     *
     * @return the rate
     */
    public Rate getRate() {
        return rate;
    }

    /**
     * Gets converted result.
     *
     * @return the converted result
     */
    public Convert getConvertedResult() {
        return convertedResult;
    }
}
