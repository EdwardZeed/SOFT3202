package model.ConcretClasses;

public class Order {
    private String good;
    private double pricePerUnit;
    private int quantity;
    private double total;

    public Order(String good, double pricePerUnit, int quantity, double total) {
        this.good = good;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.total = total;
    }

    public String toString(){
        return "Good: " + good + "\nPrice per unit: " + pricePerUnit + "\nQuantity: " + quantity + "\nTotal: " + total;
    }
}
