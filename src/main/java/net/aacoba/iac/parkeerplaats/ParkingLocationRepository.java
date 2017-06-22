package net.aacoba.iac.parkeerplaats;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParkingLocationRepository {
    private static final Map<Integer, ParkingLocation> parkinglocations = new HashMap<>();

    @PostConstruct
    public void initData() {
        ParkingLocation p1 = new ParkingLocation(1, 100);
        parkinglocations.put(p1.getId(), p1);

        ParkingLocation p2 = new ParkingLocation(2, 30);
        parkinglocations.put(p2.getId(), p2);

        ParkingLocation p3 = new ParkingLocation(3, 890);
        parkinglocations.put(p3.getId(), p3);

        ParkingLocation p4 = new ParkingLocation(4, 410);
        parkinglocations.put(p4.getId(), p4);
    }

    public ParkingLocation getParkingLocation(int id) {
        return parkinglocations.get(id);
    }

    public Map<Integer, ParkingLocation> getAll() {
        return new HashMap<Integer, ParkingLocation>(parkinglocations);
    }
}


