package mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = User.USER_IS_EMAIL_REGISTERED, query = "SELECT u FROM User u WHERE u.email =: email"),
        @NamedQuery(name = User.IS_USER_BANNED, query = "SELECT u FROM User u WHERE u.isUserBanned =: isUserBanned"),
        @NamedQuery(name = User.FIND_USER_BY_LASTNAME, query = "SELECT u FROM User u WHERE lastName =: lastName")
        })
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "[a-zA-Z]*[0-9]*.[a-zA-Z]*[0-9]*@[0-9]*[a-zA-Z]*[0-9]*.[a-zA-Z]*.[a-z]+")
    @Column(unique = true)
    private String email;
    @Column(length = 250)
    private String password;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;
    private String role;
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Message> messages = new ArrayList<>();
    private boolean isUserBanned = false;

    public static final String USER_IS_EMAIL_REGISTERED = "isEmailRegisteredInDatabase";

    public static final String IS_USER_BANNED = "isUserBanned";

    public static final String FIND_USER_BY_LASTNAME = "findUserByLastName";

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return email;
    }

    public void setMail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Message> getMessage() {
        return messages;
    }

    public void setMessage(List<Message> messages) {
        this.messages = messages;
    }

    public boolean isUserBanned() {
        return isUserBanned;
    }

    public void setUserBanned(boolean userBanned) {
        isUserBanned = userBanned;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", role='" + role + '\'' +
                ", messages=" + messages +
                ", isUserBanned=" + isUserBanned +
                '}';
    }
}
