package ca.mcgill.ecse321.rider;

import ca.mcgill.ecse321.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rider")
public class RiderController {

    @Autowired
    private RiderRepository riderRepository;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody Rider newRider) {
        if (riderRepository.findRiderByUsername(newRider.getUsername()).size() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "Rider with that username already exists"));
        }
        riderRepository.save(newRider);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Rider account successfully created"));
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody Rider login) throws ServletException {

        String jwtToken = "";

        if (login.getUsername() == null || login.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String username = login.getUsername();
        String password = login.getPassword();

        Rider rider = riderRepository.findRiderByUsername(username).get(0);

        if (rider == null) {
            throw new ServletException("Rider profile not found.");
        }

        String pwd = rider.getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Invalid login. Please check your username and password.");
        }

        jwtToken = Jwts.builder().setSubject(username).claim("roles", "rider").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "elonmusk").compact(); // setting "elonmusk" as our signing key for tokens
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, jwtToken));

    }

    @GetMapping("/secure/all")
    public ResponseEntity getAllRiders() {
        List<Rider> riders = riderRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(riders);
    }

    @PutMapping("/secure/modify/{riderid}")
    public ResponseEntity modifyRider(@PathVariable long riderid, @RequestBody Rider modifiedRider) {
        Rider newRider = riderRepository.findById(riderid)
                .map(rider -> {
                   if (modifiedRider.getUsername() != null) {
                       rider.setUsername(modifiedRider.getUsername());
                   }
                   if (modifiedRider.getPassword() != null) {
                       rider.setPassword(modifiedRider.getPassword());
                   }
                   if (modifiedRider.getName() != null) {
                       rider.setName(modifiedRider.getName());
                   }
                   if (modifiedRider.getRating() != 0) {
                       rider.setRating(modifiedRider.getRating());
                   }
                   if (modifiedRider.getNumberrides() != 0) {
                       rider.setNumberrides(modifiedRider.getNumberrides());
                   }
                   if (modifiedRider.getAge() != 0) {
                       rider.setAge(modifiedRider.getAge());
                   }
                   riderRepository.save(rider);
                   return rider;
                }).get();
        return ResponseEntity.status(HttpStatus.OK).body(newRider);
    }
}
