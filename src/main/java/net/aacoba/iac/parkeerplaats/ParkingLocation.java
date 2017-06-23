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

    public int getFreeSpots() {
        int count = 0;
        for (ParkingSpot ps: parkingSpots ) {
            if (!ps.isUsed()) {
                count++;
            }
        }
        return count;
    }

    public int getTotalCapacity() {
        return parkingSpots.size();
    }

    public List<Integer> getUsedSpots() {
        List<Integer> usedSpots = new ArrayList<>();
        for (ParkingSpot ps : parkingSpots) {
            if (ps.isUsed()) {
                usedSpots.add(ps.getId());
            }
        }
        return usedSpots;
    }

    public synchronized ParkingSpot claimAvailableSpot()  {
        for (ParkingSpot ps: parkingSpots) {
            if (!ps.isUsed()) {
                ps.setUsed(true);
                return ps;
            }
        }
        return null;
    }

    public synchronized void returnParkingSpot(int id) {
        for (ParkingSpot ps: parkingSpots) {
            if (ps.getId() == id) {
                ps.setUsed(false);
                return;
            }
        }
    }


}
