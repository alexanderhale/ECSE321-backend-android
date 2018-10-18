package ca.mcgill.ecse321.driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    @Query("select d from Driver d where d.username = :username")
    List<Driver> findDriverByUsername(@Param("username") String username);
}