package ca.mcgill.ecse321.user;

import ca.mcgill.ecse321.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.servlet.ServletException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody User newUser) {
        if (userRepository.findUserByUsername(newUser.getUsername()).size() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "User with that name already exists"));
        }
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "User successfully created"));
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody User login) throws ServletException {

        String jwtToken = "";

        if (login.getUsername() == null || login.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String username = login.getUsername();
        String password = login.getPassword();

        User user = userRepository.findUserByUsername(username).get(0);

        if (user == null) {
            throw new ServletException("User not found.");
        }

        String pwd = user.getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Invalid login. Please check your username and password.");
        }

        jwtToken = Jwts.builder().setSubject(username).claim("roles", "user").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "elonmusk").compact(); //setting "elonmusk" as our signing key for tokens
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, jwtToken));

    }
}
