package net.aacoba.iac.parkeerplaats;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingSpotTest {
    @Test
    public void testUsed() throws Exception {
        ParkingSpot ps = new ParkingSpot(1);
        ps.setUsed(true);
        assertTrue("Parking spot is not set to used!", ps.isUsed());
        ps.setUsed(false);
        assertFalse("Parking spot is not set to unused!", ps.isUsed());
    }

    @Test
    public void getId() throws Exception {
        ParkingSpot ps = new ParkingSpot(1);
        assertEquals(1, ps.getId());
        ParkingSpot ps2 = new ParkingSpot(2);
        assertEquals(2, ps2.getId());
        ParkingSpot ps3 = new ParkingSpot(3);
        assertNotEquals(4, ps3.getId());

    }

}