package mvc.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device_information")
public class DeviceInformation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="ip_address")
    private String ipAddress;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_id",
                referencedColumnName = "id")
    private Message message;

    public DeviceInformation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DeviceInformation{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                ", message=" + message +
                '}';
    }
}
