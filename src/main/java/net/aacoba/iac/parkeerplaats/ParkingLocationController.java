package net.aacoba.iac.parkeerplaats;

import net.aacoba.iac.parkeerplaats.exceptions.NoParkingSpotsAvailableException;
import net.aacoba.iac.parkeerplaats.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ParkingLocationController {
    private ParkingLocationRepository parkingLocationRepository;

    @Autowired
    public ParkingLocationController(ParkingLocationRepository parkingLocationRepository) {
        this.parkingLocationRepository = parkingLocationRepository;
    }

    @RequestMapping(value = "/parkinglocation", method = GET)
    public Map<Integer, ParkingLocation> parkingLocationList() {
        return parkingLocationRepository.getAll();
    }

    @RequestMapping(value = "/parkinglocation/{id}", method = GET)
    public ParkingLocation parkingLocation(@PathVariable("id") int id) {
        ParkingLocation foundLocation = parkingLocationRepository.getParkingLocation(id);
        if (foundLocation == null) {
            throw new ResourceNotFoundException();
        } else {
            return foundLocation;
        }
    }

    @RequestMapping(value= "/parkinglocation/{id}/claim", method = POST)
    public ParkingSpot claimParkingSpotAtLocation(@PathVariable("id") int id) {
        ParkingLocation foundLocation = parkingLocationRepository.getParkingLocation(id);
        if (foundLocation == null) {
            throw new ResourceNotFoundException();
        } else {
            ParkingSpot claimed_spot = foundLocation.claimAvailableSpot();
            if (claimed_spot == null) {
                throw new NoParkingSpotsAvailableException();
            } else {
                return claimed_spot;
            }
        }
    }

    @RequestMapping(value= "/parkinglocation/{id}/release/{spotid}", method = POST)
    public int returnParkingSpotAtLocation(@PathVariable("id") int id, @PathVariable("spotid") int spotid) {
        ParkingLocation foundLocation = parkingLocationRepository.getParkingLocation(id);
        if (foundLocation == null) {
            throw new ResourceNotFoundException();
        } else {
            foundLocation.returnParkingSpot(spotid);
            System.out.println(foundLocation.getFreeSpots());
            return spotid;
        }
    }


}
