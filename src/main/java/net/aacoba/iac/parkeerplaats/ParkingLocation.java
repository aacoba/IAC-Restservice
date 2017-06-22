package net.aacoba.iac.parkeerplaats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ParkingLocation {

    private final int id;
    private String name;
    private List<ParkingSpot> parkingSpots = Collections.synchronizedList(new ArrayList<ParkingSpot>());

    public ParkingLocation(int id, int amount_of_spots) {
        this.id = id;
        for(int i = 0; i < amount_of_spots; i++) {
            parkingSpots.add(new ParkingSpot(i));
        }
    }

    public int getId() {
        return id;
    }

    public int get_free_spots() {
        int count = 0;
        for (ParkingSpot ps: parkingSpots ) {
            if (!ps.isUsed()) {
                count++;
            }
        }
        return count;
    }

    public int get_total_capacity() {
        return parkingSpots.size();
    }

    public synchronized int claim_available_spot()  {
        for (ParkingSpot ps: parkingSpots) {
            if (!ps.isUsed()) {
                ps.setUsed(true);
                return ps.getId();
            }
        }
        return -1;
    }

    public synchronized void return_parking_spot(int id) {
        for (ParkingSpot ps: parkingSpots) {
            if (ps.getId() == id) {
                ps.setUsed(false);
                return;
            }
        }
    }


}
