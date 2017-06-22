package net.aacoba.iac.parkeerplaats;



public class ParkingSpot {
    private final int id;
    private boolean used;

    public ParkingSpot(int id) {
        this.id = id;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }

    public int getId() {
        return id;
    }
}
