package ca.mcgill.ecse321.journey;

import javax.persistence.*;

@Entity
@Table(name = "journey", schema = "public")
public class Journey {
    private long journeyid;
    private int numberOfPassengers;
    private long driver;
    private double startLat;
    private double startLong;
    private String startAddress;
    private String startCity;
    private String startCountry;
    private double endLat;
    private double endLong;
    private String endAddress;
    private String endCity;
    private String endCountry;

    @Id
    @GeneratedValue(generator = "journeyid_generator")
    @SequenceGenerator(
            name = "journeyid_generator",
            sequenceName = "journey_journeyid_seq",
            allocationSize = 1
    )
    public long getJourneyid() {
        return journeyid;
    }

    public void setJourneyid(long journeyid) {
        this.journeyid = journeyid;
    }


    @Basic
    @Column(name = "numpassengers")
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassangers) {
        this.numberOfPassengers = numberOfPassangers;
    }


    @Basic
    @Column(name = "driver")
    public long getDriver() {
        return driver;
    }

    public void setDriver(long driverid) {
        this.driver = driverid;
    }

    @Basic
    @Column(name = "startlat")
    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    @Basic
    @Column(name = "startlong")
    public double getStartLong() {
        return startLong;
    }

    public void setStartLong(double startLong) {
        this.startLong = startLong;
    }

    @Basic
    @Column(name = "startaddress")
    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    @Basic
    @Column(name = "startcity")
    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    @Basic
    @Column(name = "startcountry")
    public String getStartCountry() {
        return startCountry;
    }

    public void setStartCountry(String startCountry) {
        this.startCountry = startCountry;
    }

    @Basic
    @Column(name = "endlat")
    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    @Basic
    @Column(name = "endlong")
    public double getEndLong() {
        return endLong;
    }

    public void setEndLong(double endLong) {
        this.endLong = endLong;
    }

    @Basic
    @Column(name = "endaddress")
    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    @Basic
    @Column(name = "endcity")
    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    @Basic
    @Column(name = "endcountry")
    public String getEndCountry() {
        return endCountry;
    }

    public void setEndCountry(String endCountry) {
        this.endCountry = endCountry;
    }

}
