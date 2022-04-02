package model;

public class SpaceTraderOffline {
    private DummyAPI api = new DummyAPI();

    public boolean isAvailable() {
        return api.isAvailable();
    }
}
