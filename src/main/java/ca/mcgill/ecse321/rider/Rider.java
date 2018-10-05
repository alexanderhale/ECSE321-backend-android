package ca.mcgill.ecse321.rider;
import javax.persistence.*;

@Entity
@Table(name = "rider", schema = "public")
public class Rider {
    private long riderid;
    private String username;
    private String password;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rider that = (Rider) o;

        if (riderid != that.riderid) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }
}
