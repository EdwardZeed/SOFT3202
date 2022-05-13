package model;

import java.io.IOException;
import java.net.URISyntaxException;

public interface CurrencyScoop {
    Convert convert(String from, String to, double amount) throws URISyntaxException, IOException, InterruptedException;
    Rate getRate(String from, String to) throws URISyntaxException, IOException, InterruptedException;
}
