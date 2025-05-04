package mvc.service;

import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import mvc.model.EventID;
import mvc.model.dto.MessageDTO;
import mvc.model.entity.DeviceInformation;
import mvc.model.entity.ErrorType;
import mvc.model.entity.Message;
import mvc.model.entity.User;
import mvc.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Slf4j
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    @Transactional
    public void addMessages(MessageDTO messageDTO, String remoteHostName, User user){
        try {
            Message message = new Message();
            DeviceInformation deviceInformation = new DeviceInformation();
            deviceInformation.setIpAddress(remoteHostName);
            message.setUser(user);
            message.setMessage(messageDTO.getMessage());
            message.setUploadDir(messageDTO.getUploadDir());
            message.setLocalDate(new Date());
            message.setDeviceInformation(deviceInformation);
            messageRepository.save(message);
        }catch (PersistenceException e){
            log.info(ErrorType.PERSISTENCE_ERROR + " " + EventID.TW0005 + " " + " Message didn't add");
        }
    }

    public void deleteMessage(Long id){
        messageRepository.deleteById(id);
    }
}
