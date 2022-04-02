package model.ConcretClasses;

import java.util.ArrayList;

public class User {
    private String joinedAt;
    private int shipCount;
    private int structureCount;
    private String username;
    private int credits;
    private ArrayList<Loan> loans = new ArrayList<>();
    private ArrayList<Ship> ships = new ArrayList<>();
    private String token;
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User(String username, int credits, String joinedAt, int shipCount, int StructureCount) {
        this.username = username;
        this.credits = credits;
        this.joinedAt = joinedAt;
        this.shipCount = shipCount;
        this.structureCount = StructureCount;
    }

    public String getUsername() {
        return username;
    }

    public int getCredits() {
        return credits;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public String getJoinDate() {
        return joinedAt;
    }

    public int getShipCount() {
        return shipCount;
    }

    public int getStructureCount() {
        return structureCount;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public void setShips(ArrayList<Ship> shipList) {
        ships = shipList;
    }

    public Ship getShip(String shipId) {
        for (Ship ship : ships) {
            if (ship.getId().equals(shipId)) {
                return ship;
            }
        }
        return null;
    }
}
