package ca.mcgill.ecse321.hello;

import ca.mcgill.ecse321.model.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@RestController
@SpringBootApplication
public class MainApp {

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);

    }

    @RequestMapping("/")
    public String greeting() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String temp = "";

        List<User> listUsernames = entityManager.createQuery(
                "SELECT p FROM User p").getResultList();

        if (listUsernames == null) {
            temp = "no usernames found";
        } else {
            for (User user : listUsernames) {
                temp += user.getUsername() + " ";
            }
        }

        return temp;
    }
}