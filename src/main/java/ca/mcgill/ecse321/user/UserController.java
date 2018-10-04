package ca.mcgill.ecse321.user;

import ca.mcgill.ecse321.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody User newUser) {
        if (userRepository.findUserByUsername(newUser.getUsername()).size() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "User with that name already exists"));
        }
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "User successfully created"));
    }
}
