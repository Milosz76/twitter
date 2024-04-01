package mvc.model.dto;


import mvc.model.entity.DeviceInformation;
import mvc.model.entity.User;

public class MessageDTO {

    private Long id;
    private String message;
    private String uploadDir;
    private String localDate;
    private UserDTO userDTO;
    private DeviceInformationDTO deviceInformationDTO;

    public MessageDTO() {
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

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public DeviceInformationDTO getDeviceInformationDTO() {
        return deviceInformationDTO;
    }

    public void setDeviceInformationDTO(DeviceInformationDTO deviceInformationDTO) {
        this.deviceInformationDTO = deviceInformationDTO;
    }
}
