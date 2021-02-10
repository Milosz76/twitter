package mvc.controller;

import com.sun.tools.internal.ws.processor.model.Model;
import mvc.model.dto.MessageDTO;
import mvc.model.dto.UserDTO;
import mvc.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import java.util.List;

@Controller
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/useraddcontent")
    public ModelAndView getAddNewMessage(){
        logger.warn("Before add a new message");
        return new ModelAndView("useraddcontent", "userAddMessage", new MessageDTO());
    }

    @PostMapping("/useraddcontent")
    public String addNewMessage(@ModelAttribute MessageDTO messageDTO){
        logger.warn("Creating a new message");
        System.out.println(messageDTO.getMessage());
        messageService.addMessage(messageDTO);
        return "userpage";
    }

    @GetMapping("/show-messages")
    public ModelAndView readMessages(){
        logger.warn("Reading messages");
        List<MessageDTO> messageDTOList = messageService.getAllMessages();
        return new ModelAndView("show-messages", "userAddMessage", messageDTOList);
    }

}
