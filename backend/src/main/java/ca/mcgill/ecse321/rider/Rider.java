package ca.mcgill.ecse321.rider;

import javax.persistence.*;

@Entity
@Table(name = "rider", schema = "public")
public class Rider {
    private long riderid;
    private String username;
    private String password;
    private String name;
    private int rating;
    private int numberrides;
    private int age;
    private boolean status;

    @Id
    @GeneratedValue(generator = "riderid_generator")
    @SequenceGenerator(
            name = "riderid_generator",
            sequenceName = "rider_riderid_seq",
            allocationSize = 1
    )
    public long getRiderid() {
        return riderid;
    }

    public void setRiderid(long userid) {
        this.riderid = userid;
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
        Rider rider = (Rider) object;
        return riderid == rider.riderid &&
                rating == rider.rating &&
                numberrides == rider.numberrides &&
                age == rider.age &&
                status == rider.status &&
                java.util.Objects.equals(username, rider.username) &&
                java.util.Objects.equals(password, rider.password) &&
                java.util.Objects.equals(name, rider.name);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), riderid, username, password, name, rating, numberrides, age, status);
    }
}
