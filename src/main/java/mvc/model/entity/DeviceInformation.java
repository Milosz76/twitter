package mvc.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device_information")
public class DeviceInformation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", name='" + name + '\'' +
                ", message=" + message +
                '}';
    }
}
