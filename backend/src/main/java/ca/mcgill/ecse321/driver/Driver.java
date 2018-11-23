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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver that = (Driver) o;

        if (driverid != that.driverid) return false;
        if (rating != that.rating) return false;
        if (numberrides != that.numberrides) return false;
        if (age != that.age) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (carModel != null ? !carModel.equals(that.carModel) : that.carModel != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }
}

