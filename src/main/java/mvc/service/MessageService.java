package mvc.service;

import mvc.model.dto.MessageDTO;
import mvc.model.dto.UserDTO;
import mvc.model.entity.Message;
import mvc.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final ModelMapper modelMapper;
    private final MessageRepository messageRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    public MessageService(ModelMapper modelMapper, MessageRepository messageRepository) {
        this.modelMapper = modelMapper;
        this.messageRepository = messageRepository;
    }

    public void addMessage(MessageDTO messageDTO){
        Message message = modelMapper.map(messageDTO, Message.class);
        messageRepository.save(message);
    }

    public void deleteMessage(Long id){
        messageRepository.deleteById(id);
    }

    public List<MessageDTO> getAllMessages(){
        return messageRepository
                .findAll()
                .stream()
                .map(message -> modelMapper.map(message, MessageDTO.class))
                .collect(Collectors.toList());
    }
}
