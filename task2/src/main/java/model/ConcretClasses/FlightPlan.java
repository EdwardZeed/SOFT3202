package model.ConcretClasses;

public class FlightPlan {
    private String arrivesAt;
    private String departure;
    private String destination;
    private int distance;
    private int fuelConsumed;
    private int fuelRemaining;
    private String id;
    private String shipId;
    private String terminatedAt;
    private int timeRemainingInSeconds;

    public FlightPlan(String arrivesAt, String departure, String destination, int distance, int fuelConsumed, int fuelRemaining, String id, String shipId, String terminatedAt, int timeRemainingInSeconds) {
        this.arrivesAt = arrivesAt;
        this.departure = departure;
        this.destination = destination;
        this.distance = distance;
        this.fuelConsumed = fuelConsumed;
        this.fuelRemaining = fuelRemaining;
        this.id = id;
        this.shipId = shipId;
        this.terminatedAt = terminatedAt;
        this.timeRemainingInSeconds = timeRemainingInSeconds;
    }

    @Override
    public String toString() {
        return "arrivesAt='" + arrivesAt + '\n' +
                ", departure='" + departure + '\n' +
                ", destination='" + destination + '\n' +
                ", distance=" + distance + '\n' +
                ", fuelConsumed=" + fuelConsumed + '\n' +
                ", fuelRemaining=" + fuelRemaining + '\n' +
                ", id='" + id + '\n' +
                ", shipId='" + shipId + '\n' +
                ", terminatedAt='" + terminatedAt + '\n' +
                ", timeRemainingInSeconds=" + timeRemainingInSeconds;
    }
}
