package mvc.service;

import mvc.model.dto.MessageDTO;
import mvc.model.entity.Message;
import mvc.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MessageService {

    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final MessageRepository messageRepository;

    public MessageService(ModelMapper modelMapper, MessageRepository messageRepository) {
        this.modelMapper = modelMapper;
        this.messageRepository = messageRepository;
    }

    //dodac sprawdzenie - jesli uzytkownik w bazie danych jest zablokowany to nie moze publikowac wiadomosci
    public void addMessages(MessageDTO messageDTO){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        messageDTO.setLocalDate(now.format(formatter));

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

    //find all massages written by user based on user login id and date

}
