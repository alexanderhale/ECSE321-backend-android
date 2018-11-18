package ca.mcgill.ecse321.journey;


import ca.mcgill.ecse321.api.ApiResponse;
import ca.mcgill.ecse321.driver.Driver;
import ca.mcgill.ecse321.driver.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/journey")
public class JourneyController {

    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private DriverRepository driverRepository;

    @PostMapping("/create")
    public ResponseEntity createJourney(@RequestBody Journey newJourney) {
        journeyRepository.save(newJourney);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Journey successfully created"));
    }

    @GetMapping("/secure/all")
    public ResponseEntity allJourneys(HttpServletRequest req) {
        Map<String, String> claims = (Map<String, String>) req.getAttribute("claims");
        String username = claims.get("sub");

        Driver driver = driverRepository.findDriverByUsername(username).get();
        Long driverId = driver.getDriverid();

        List<Journey> all = journeyRepository.findJourniesByDriver(driverId);
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }
    @GetMapping("/all")
    public ResponseEntity getAllJourneys() {
        List<Journey> journeys = journeyRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(journeys);
    }


    @GetMapping("/{journeyid}/me")
    public ResponseEntity me(@PathVariable long journeyid) {
        Journey journey = journeyRepository.findJourneyById(journeyid).get();
        return ResponseEntity.status(HttpStatus.OK).body(journey);
    }
    @GetMapping("/{journeyid}/driver")
    public ResponseEntity getDriver(@PathVariable long journeyid) {
        long driverid = journeyRepository.findDriverId(journeyid);
        Optional<Driver> drivers = driverRepository.findById(driverid);
        return ResponseEntity.status(HttpStatus.OK).body(drivers);
    }

    @PostMapping("/{journeyid}/addRider/{riderid}")
    public ResponseEntity addRiderToJourney(@PathVariable long journeyid, @PathVariable long riderid) {
        journeyRepository.addRider(journeyid, riderid);

        journeyRepository.findById(journeyid)
                .map(journey -> {
                    journey.setNumberOfPassengers(journey.getNumberOfPassengers() + 1);
                    journeyRepository.save(journey);
                    return journey;
                });

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Passenger added"));
    }

    @PostMapping("/{journeyid}/removeRider/{riderid}")
    public ResponseEntity removeRiderFromJourney(@PathVariable long journeyid, @PathVariable long riderid) {
        journeyRepository.removeRider(riderid);

        journeyRepository.findById(journeyid)
                .map(journey -> {
                    journey.setNumberOfPassengers(journey.getNumberOfPassengers() - 1);
                    journeyRepository.save(journey);
                    return journey;
                });
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Passenger removed"));
    }

    @PutMapping("/{journeyid}/modify")
    public ResponseEntity modifyJourney(@PathVariable long journeyid, @RequestBody Journey modifiedJourney) {
        Journey newJourney = journeyRepository.findById(journeyid).map(journey -> {
            if (modifiedJourney.getStartLat() != 0.0) {
                journey.setStartLat(modifiedJourney.getStartLat());
            }
            if (modifiedJourney.getStartLong() != 0.0) {
                journey.setStartLong(modifiedJourney.getStartLong());
            }
            if (modifiedJourney.getStartAddress() != null) {
                journey.setStartAddress(modifiedJourney.getStartAddress());
            }
            if (modifiedJourney.getStartCity() != null) {
                journey.setStartCity(modifiedJourney.getStartCity());
            }
            if (modifiedJourney.getStartCountry() != null) {
                journey.setStartCountry(modifiedJourney.getStartCountry());
            }
            if (modifiedJourney.getEndLat() != 0.0) {
                journey.setEndLat(modifiedJourney.getEndLat());
            }
            if (modifiedJourney.getEndLong() != 0.0) {
                journey.setEndLong(modifiedJourney.getEndLong());
            }
            if (modifiedJourney.getEndAddress() != null) {
                journey.setEndAddress(modifiedJourney.getEndAddress());
            }
            if (modifiedJourney.getEndCity() != null) {
                journey.setEndCity(modifiedJourney.getEndCity());
            }
            if (modifiedJourney.getEndCountry() != null) {
                journey.setEndCountry(modifiedJourney.getEndCountry());
            }
            if (modifiedJourney.getNumberOfPassengers() != 0) {
                journey.setNumberOfPassengers(modifiedJourney.getNumberOfPassengers());
            }
            if (modifiedJourney.getCapacity() != 0) {
                journey.setCapacity(modifiedJourney.getCapacity());
            }
            if (modifiedJourney.getPrice() != 0) {
                journey.setPrice(modifiedJourney.getPrice());
            }
            if (modifiedJourney.getTimePickup() != null) {
                journey.setTimePickup(modifiedJourney.getTimePickup());
            }
            if (modifiedJourney.getRating() != 0) {
                journey.setRating(modifiedJourney.getRating());
            }

            journeyRepository.save(journey);

            return journey;
        }).get();

        return ResponseEntity.status(HttpStatus.OK).body(newJourney);
    }
    @PutMapping("/{journeyid}/close")
    public ResponseEntity closeJourney(@PathVariable long journeyid) {
        Journey newJourney = journeyRepository.findById(journeyid).map(journey -> {
            journey.setClosed(true);
            journeyRepository.save(journey);
            return journey;
        }).get();
        return ResponseEntity.status(HttpStatus.OK).body(newJourney);
    }
    @PutMapping("/{journeyid}/open")
    public ResponseEntity openJourney(@PathVariable long journeyid) {
        Journey newJourney = journeyRepository.findById(journeyid).map(journey -> {
            journey.setClosed(false);
            journeyRepository.save(journey);
            return journey;
        }).get();
        return ResponseEntity.status(HttpStatus.OK).body(newJourney);
    }
}
