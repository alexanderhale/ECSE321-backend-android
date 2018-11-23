package ca.mcgill.ecse321.driver;

import javax.persistence.*;

@Entity
@Table(name = "driver", schema = "public")
public class Driver {
    private long driverid;
    private String username;
    private String password;
    private String carModel;
    private String name;
    private int rating;
    private int numberrides;
    private int age;
    private boolean status;

    @Id
    @GeneratedValue(generator = "driverid_generator")
    @SequenceGenerator(
            name = "driverid_generator",
            sequenceName = "driver_driverid_seq",
            allocationSize = 1
    )
    public long getDriverid() {
        return driverid;
    }

    public void setDriverid(long userid) {
        this.driverid = userid;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "carmodel")
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "rating")
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "numberrides")
    public int getNumberrides() {
        return numberrides;
    }

    public void setNumberrides(int numberrides) {
        this.numberrides = numberrides;
    }

    @Basic
    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Basic
    @Column(name = "status")
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Driver driver = (Driver) object;
        return driverid == driver.driverid &&
                rating == driver.rating &&
                numberrides == driver.numberrides &&
                age == driver.age &&
                status == driver.status &&
                java.util.Objects.equals(username, driver.username) &&
                java.util.Objects.equals(password, driver.password) &&
                java.util.Objects.equals(carModel, driver.carModel) &&
                java.util.Objects.equals(name, driver.name);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), driverid, username, password, carModel, name, rating, numberrides, age, status);
    }
}

