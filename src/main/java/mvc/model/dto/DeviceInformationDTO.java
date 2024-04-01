package mvc.model.dto;



public class DeviceInformationDTO {

    private Long id;
    private String name;
    private MessageDTO messageDTO;


    public DeviceInformationDTO() {
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
}
