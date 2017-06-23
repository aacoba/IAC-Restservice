package net.aacoba.iac.parkeerplaats;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class ParkingLocationTest {

    @Test
    public void getId() throws Exception {
        ParkingLocation p = new ParkingLocation(1, 100);
        Assert.assertEquals(1, p.getId());
    }

    @Test
    public void getFreeSpots() throws Exception {
        ParkingLocation p = new ParkingLocation(1, 1334);
        p.claimAvailableSpot();
        p.claimAvailableSpot();
        assertEquals(1332, p.getFreeSpots());
    }

    @Test
    public void getTotalCapacity() throws Exception {
        ParkingLocation p = new ParkingLocation(1, 1337);
        assertEquals(1337, p.getTotalCapacity());
    }

    @Test
    public void getUsedSpots() throws Exception {
        List<Integer> claimed_spots = new ArrayList<>();
        ParkingLocation p = new ParkingLocation(1, 1337);
        claimed_spots.add(p.claimAvailableSpot().getId());
        claimed_spots.add(p.claimAvailableSpot().getId());
        claimed_spots.add(p.claimAvailableSpot().getId());
        claimed_spots.add(p.claimAvailableSpot().getId());
        claimed_spots.add(p.claimAvailableSpot().getId());
        claimed_spots.add(p.claimAvailableSpot().getId());
        assertEquals(claimed_spots, p.getUsedSpots());
        p.claimAvailableSpot();
        p.claimAvailableSpot();
        assertNotEquals(claimed_spots, p.getUsedSpots());
    }

    @Test
    public void claimAvailableSpot() throws Exception {
        ParkingLocation p = new ParkingLocation(1, 1337);
        int claimed_spot_id = p.claimAvailableSpot().getId();

        boolean in_used_spots = isSpotUsed(p, claimed_spot_id);
        assertTrue("Claimed not not in claimed spots list", in_used_spots);

    }

    @Test
    public void returnParkingSpot() throws Exception {
        ParkingLocation p = new ParkingLocation(1, 1337);
        int claimed_spot_id = p.claimAvailableSpot().getId();

        boolean in_used_spots = isSpotUsed(p, claimed_spot_id);
        assertTrue("Claimed not not in claimed spots list", in_used_spots);

        p.returnParkingSpot(claimed_spot_id);
        boolean in_used_spots_again = isSpotUsed(p, claimed_spot_id);
        assertFalse("Claimed not not in claimed spots list", in_used_spots_again);
    }

    private boolean isSpotUsed(ParkingLocation parkingLocation, int spotId) {
        boolean in_used_spots = false;
        for (Integer ps_id : parkingLocation.getUsedSpots()) {
            if (ps_id == spotId) {
                in_used_spots = true;
            }
        }
        return in_used_spots;
    }

}