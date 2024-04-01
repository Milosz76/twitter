package mvc.model.dto;

import mvc.model.entity.Message;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDTO {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date birthDate;
    private String role;
    private List<Message> message = new ArrayList<>();
    private boolean isUserBanned;

    public UserDTO() {
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
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public boolean isUserBanned() {
        return isUserBanned;
    }

    public void setUserBanned(boolean userBanned) {
        isUserBanned = userBanned;
    }
}
