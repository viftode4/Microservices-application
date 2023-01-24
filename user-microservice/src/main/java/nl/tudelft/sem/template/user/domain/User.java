package nl.tudelft.sem.template.user.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "net_id", nullable = false, unique = true)
    private String netId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "role", nullable = false)
    @Convert(converter = UserRoleConverter.class)
    private UserRole userRole;

    /**
     * Default constructor.
     *
     * @param firstName - first name of the user
     * @param lastName - last name of the user
     * @param address - address of the user
     * @param netId - netId of the user
     * @param userRole - role of the user
     */
    public User(String firstName, String lastName, String address, String netId, UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.netId = netId;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User "
            + netId + '\''
            + ", first name='" + firstName + '\''
            + ", last name='" + lastName + '\''
            + ", with role=" + userRole;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }
}
