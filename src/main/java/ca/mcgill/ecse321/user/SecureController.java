package ca.mcgill.ecse321.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.user.UserRepository;
import ca.mcgill.ecse321.user.User;

import java.util.List;

@RestController
@RequestMapping("/secure")
public class SecureController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    // TO BE REMOVED - FOR TESTING PURSPOSES ONLY - TESTING TOKEN SUCCESS
    // OBVIOUSLY NOT SECURE

    @GetMapping("/delete_all_users")
    public ResponseEntity deleteUser(){
        userRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All users deleted successfully");
    }

}
