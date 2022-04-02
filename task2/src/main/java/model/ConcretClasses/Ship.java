package model.ConcretClasses;

import java.util.ArrayList;

public class Ship {
    private String className;
    private int maxCargo;
    private String manufacturer;
    private int plating;
    private ArrayList<Location> purchaseLocations;
    private double speed;
    private String type;
    private int weapons;
    private String id;
    private int spaceAvailable;
    private double x;
    private double y;
    private ArrayList<Cargo> cargo;
    private FlightPlan flightPlan;

    public Ship(String className, int maxCargo, String manufacturer, int plating, ArrayList<Location> purchaseLocations, double speed, String type, int weapons, String id, int spaceAvailable, double x, double y) {
        this.className = className;
        this.maxCargo = maxCargo;
        this.manufacturer = manufacturer;
        this.plating = plating;
        this.purchaseLocations = purchaseLocations;
        this.speed = speed;
        this.type = type;
        this.weapons = weapons;
        this.id = id;
        this.spaceAvailable = spaceAvailable;
        this.x = x;
        this.y = y;
    }

    public Ship(String className, String manufacturer, int maxCargo, int plating, ArrayList<Location> purchaseLocations, double speed, String type, int weapons){
        this.className = className;
        this.manufacturer = manufacturer;
        this.maxCargo = maxCargo;
        this.plating = plating;
        this.purchaseLocations = purchaseLocations;
        this.speed = speed;
        this.type = type;
        this.weapons = weapons;
    }

    public String getClassName() {
        return className;
    }

    public int getMaxCargo() {
        return maxCargo;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getPlating() {
        return plating;
    }

    public ArrayList<Location> getPurchaseLocations() {
        return purchaseLocations;
    }

    public double getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }

    public int getWeapons() {
        return weapons;
    }

    public String toString(){
        return "Class Name: " + className + "\n" +
                "Manufacturer: " + manufacturer + "\n" +
                "Max Cargo: " + maxCargo + "\n" +
                "Plating: " + plating + "\n" +
                "Purchase Locations: " + purchaseLocations + "\n" +
                "Speed: " + speed + "\n" +
                "Type: " + type + "\n" +
                "Weapons: " + weapons;
    }

    public String purchaseShipString(){
        return "cargo: " + cargo + "\n" +
                "class:" + className + "\n" +
                "id: " + String.valueOf(id) + "\n" +
                "location: "  + purchaseLocations + "\n" +
                "manufacturer:" + manufacturer + "\n" +
                "maxCargo: " + maxCargo + "\n" +
                "plating: " + plating + "\n" +
                "spaceAvailable: " + spaceAvailable + "\n" +
                "speed: " + speed + "\n" +
                "type: " + type + "\n" +
                "weapons: " + weapons + "\n" +
                "x: " + String.valueOf(x) + "\n" +
                "y: " + String.valueOf(y);
    }


    public void setClassName(String aClass) {
        this.className = aClass;
    }

    public void setFlightPlan(FlightPlan flightPlan) {
        this.flightPlan = flightPlan;
    }

    public String getId() {
        return id;
    }

    public FlightPlan getFlightPlan() {
        return flightPlan;
    }
}
