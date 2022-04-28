package model;

import java.net.URISyntaxException;

public interface CurrencyScoop {
    double convert(String from, String to, double amount);
    double getRate(String from, String to) throws URISyntaxException;
}
