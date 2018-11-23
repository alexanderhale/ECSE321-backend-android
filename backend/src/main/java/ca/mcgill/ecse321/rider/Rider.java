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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rider that = (Rider) o;

        if (riderid != that.riderid) return false;
        if (rating != that.rating) return false;
        if (numberrides != that.numberrides) return false;
        if (age != that.age) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;


        return true;
    }
}
