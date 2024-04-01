package mvc.service;

import mvc.model.dto.DeviceInformationDTO;
import mvc.model.entity.DeviceInformation;
import mvc.repository.DeviceInformationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceInformationService {

    @Autowired
    private DeviceInformationRepository deviceInformationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public DeviceInformationService(DeviceInformationRepository deviceInformationRepository, ModelMapper modelMapper) {
        this.deviceInformationRepository = deviceInformationRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Get device information during listing the massages and write it to the database
     * @param deviceInformationDTO
     * @return deviceInformation
     */
    public DeviceInformation save(DeviceInformationDTO deviceInformationDTO){
        DeviceInformation deviceInformation = modelMapper.map(deviceInformationDTO, DeviceInformation.class);
        return deviceInformationRepository.save(deviceInformation);
    }
}
