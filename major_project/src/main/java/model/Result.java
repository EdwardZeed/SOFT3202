package model;

public final class Result {
    private Rate rate;
    private Convert convertedResult;

    public Result(Rate rate, Convert convertedResult) {
        this.rate = rate;
        this.convertedResult = convertedResult;
    }

    public Rate getRate() {
        return rate;
    }

    public Convert getConvertedResult() {
        return convertedResult;
    }
}
