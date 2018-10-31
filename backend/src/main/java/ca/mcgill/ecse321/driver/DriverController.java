package ca.mcgill.ecse321.driver;

import ca.mcgill.ecse321.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody Driver newDriver) {
        if (driverRepository.findDriverByUsername(newDriver.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "Driver with that username already exists."));
        }
        driverRepository.save(newDriver);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Driver account successfully created."));
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody Driver login) throws ServletException {

        String jwtToken = "";

        if (login.getUsername() == null || login.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String username = login.getUsername();
        String password = login.getPassword();

        Optional<Driver> driver = driverRepository.findDriverByUsername(username);

        if (!driver.isPresent()) {
            throw new ServletException("Driver profile not found.");
        }

        String pwd = driver.get().getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Invalid login. Please check your username and password.");
        }

        jwtToken = Jwts.builder().setSubject(username).claim("roles", "driver").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "elonmusk").compact(); // setting "elonmusk" as our signing key for tokens
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, jwtToken));

    }

    @GetMapping("/secure/me")
    public ResponseEntity me(HttpServletRequest req) {
        Map<String, String> claims = (Map<String, String>) req.getAttribute("claims");
        String username = claims.get("sub");

        Driver driver = driverRepository.findDriverByUsername(username).get();
        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @PutMapping("/secure/modify")
    public ResponseEntity updateCarModel(@RequestBody Driver modifiedDriver, HttpServletRequest req) {
        Map<String, String> claims = (Map<String, String>) req.getAttribute("claims");
        String username = claims.get("sub");
        Driver newDriver = driverRepository.findDriverByUsername(username)
                .map(driver -> {
                    if (modifiedDriver.getUsername() != null) {
                        driver.setUsername(modifiedDriver.getUsername());
                    }
                    if (modifiedDriver.getPassword() != null) {
                        driver.setPassword(modifiedDriver.getPassword());
                    }
                    if (modifiedDriver.getName() != null) {
                        driver.setName(modifiedDriver.getName());
                    }
                    if (modifiedDriver.getRating() != 0) {
                        driver.setRating(modifiedDriver.getRating());
                    }
                    if (modifiedDriver.getNumberrides() != 0) {
                        driver.setNumberrides(modifiedDriver.getNumberrides());
                    }
                    if (modifiedDriver.getAge() != 0) {
                        driver.setAge(modifiedDriver.getAge());
                    }
                    if (modifiedDriver.getCarModel() != null) {
                        driver.setCarModel(modifiedDriver.getCarModel());
                    }
                    driverRepository.save(driver);
                    return driver;
                }).get();
        return ResponseEntity.status(HttpStatus.OK).body(newDriver);
    }

    @GetMapping("/secure/all")
    public ResponseEntity getAllDrivers() {
        List<Driver> drivers = driverRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(drivers);
    }
}
