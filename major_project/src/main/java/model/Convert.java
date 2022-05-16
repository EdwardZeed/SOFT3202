package model;

public final class Convert {
    private double result;
    private String from;
    private String to;

    public Convert(double result, String from, String to) {
        this.result = result;
        this.from = from;
        this.to = to;
    }

    public double getResult() {
        return result;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
