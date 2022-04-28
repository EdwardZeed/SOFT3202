package model.offline;

import model.CurrencyScoop;

public class CurrencyScoopOffline implements CurrencyScoop {
    public double convert(String from, String to, double amount) {
        return 70;
    }
    public double getRate(String from, String to) {
        return 7.0;
    }
}
