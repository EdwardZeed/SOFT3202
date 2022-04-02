package model.ConcretClasses;

public class Location {
    private String location;
    private double price;
    private String name;
    private String type;
    private int x;
    private int y;
    public Location(String location, double price){
        this.location = location;
        this.price = price;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Location(String symbol, String type, int x, int y) {
        this.location = symbol;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public String getSymbol() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    public String toString(){
        return "Location: " + location + " Price: " + price;
    }

    public String DetailedString(){
        return "name: " + name + "\nsymbol: " + location + "\ntype: " + type  + "\nx: " + x + "\ny: " + y;

    }
}
