package mvc.model.entity;

import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Size(max=350)
    private String message;
    private String uploadDir;
    @Column(name = "local_date")
    private Date localDate;
    @ManyToOne
    @JoinColumn(name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deviceInformation_id",
            referencedColumnName = "id"
    )
    private DeviceInformation deviceInformation;


    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public Date getLocalDate() {
        return localDate;
    }

    public void setLocalDate(Date localDate) {
        this.localDate = localDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DeviceInformation getDeviceInformation() {
        return deviceInformation;
    }

    public void setDeviceInformation(DeviceInformation deviceInformation) {
        this.deviceInformation = deviceInformation;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", uploadDir='" + uploadDir + '\'' +
                ", localDate='" + localDate + '\'' +
                ", user=" + user +
                ", deviceInformation=" + deviceInformation +
                '}';
    }
}
