package ca.mcgill.ecse321.driver;

import ca.mcgill.ecse321.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody Driver newDriver) {
        if (driverRepository.findDriverByUsername(newDriver.getUsername()).size() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "Driver with that username already exists"));
        }
        driverRepository.save(newDriver);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Driver account successfully created"));
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody Driver login) throws ServletException {

        String jwtToken = "";

        if (login.getUsername() == null || login.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String username = login.getUsername();
        String password = login.getPassword();

        Driver driver = driverRepository.findDriverByUsername(username).get(0);

        if (driver == null) {
            throw new ServletException("Driver profile not found.");
        }

        String pwd = driver.getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Invalid login. Please check your username and password.");
        }

        jwtToken = Jwts.builder().setSubject(username).claim("roles", "driver").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "elonmusk").compact(); // setting "elonmusk" as our signing key for tokens
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, jwtToken));

    }

    @PutMapping("/secure/updateCarModel/{id}")
    public ResponseEntity updateCarModel(@PathVariable long id, @RequestBody Map<String, String> payload) {
        System.out.println(payload);
        driverRepository.findById(id)
                .map(driver -> {
                    driver.setCarModel(payload.get("carModel"));
                    driverRepository.save(driver);
                    return driver;
                });
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Car model updated!"));
    }

    @GetMapping("/secure/allDrivers")
    public ResponseEntity getAllDrivers() {
        List<Driver> drivers = driverRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(drivers);
    }
}
