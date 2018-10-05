package ca.mcgill.ecse321.driver;

import javax.persistence.*;

@Entity
@Table(name = "driver", schema = "public")
public class Driver {
    private int driverid;
    private String username;
    private String password;
    private String carModel;

    @Id
    @GeneratedValue(generator = "driverid_generator")
    @SequenceGenerator(
            name = "driverid_generator",
            sequenceName = "driver_driverid_seq",
            allocationSize = 1
    )
    public int getDriverid() { return driverid; }

    public void setDriverid(int userid) {
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
    public String getCarModel() { return carModel; }

    public void setCarModel(String carModel) { this.carModel = carModel; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver that = (Driver) o;

        if (driverid != that.driverid) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = driverid;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}

