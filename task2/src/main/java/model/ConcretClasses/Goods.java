package model.ConcretClasses;

public class Goods {
    private int pricePerUnit;
    private int quantityAvailable;
    private String symbol;
    private double volumePerUnit;

    public Goods(int pricePerUnit, int quantityAvailable, String symbol, double volumePerUnit) {
        this.pricePerUnit = pricePerUnit;
        this.quantityAvailable = quantityAvailable;
        this.symbol = symbol;
        this.volumePerUnit = volumePerUnit;
    }

    public String toString(){
        return "Price per unit: " + pricePerUnit + "\nQuantity available: " + quantityAvailable + "\nSymbol: " + symbol + "\nVolume per unit: " + volumePerUnit;
    }
}
