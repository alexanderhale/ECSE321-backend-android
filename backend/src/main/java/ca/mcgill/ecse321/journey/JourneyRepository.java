package ca.mcgill.ecse321.journey;

import ca.mcgill.ecse321.driver.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {
    @Query(value = "SELECT Driver FROM Driver INNER JOIN Journey ON Driver.driverid = Journey.driver WHERE Journey.journeyid = :journeyid",
            nativeQuery = true)
    long findDriverId(@Param("journeyid") long journeyid);

    @Query(value = "WITH rows AS (\n" +
            "  INSERT into rider_journey (riderid, journeyid) VALUES (:riderid, :journeyid)\n" +
            "  RETURNING riderid\n" +
            ") SELECT riderid from rows;", nativeQuery = true)
    long addRider(@Param("journeyid") long journeyid, @Param("riderid") long riderid);

    @Query(value = "DELETE FROM rider_journey WHERE riderid = :riderid RETURNING riderid", nativeQuery = true)
    long removeRider(@Param("riderid") long riderid);

    @Query(value = "SELECT j FROM Journey j WHERE j.driver = :driver ORDER BY j.journeyid")
    List<Journey> findJourniesByDriver(@Param("driver") long driver);

    @Query("select j from Journey j where j.journeyid = :journeyid")
    Optional<Journey> findJourneyById(@Param("journeyid") long journeyid);
}