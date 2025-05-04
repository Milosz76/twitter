package mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import mvc.model.EventID;
import mvc.model.dto.MessageDTO;
import mvc.model.entity.Message;
import mvc.model.entity.User;
import mvc.repository.MessageRepository;
import mvc.service.MessageService;
import mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;


@Controller
public class MessageController {

    private static final Logger loggerSlf4j = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageRepository messageRepository;



    @GetMapping("/useraddcontent")
    public ModelAndView getAddNewMessage(){
        loggerSlf4j.warn("Before add a new message");
        return new ModelAndView("useraddcontent", "userAddMessage", new MessageDTO());
    }

    @PostMapping("/useraddcontent")
    public String addNewMessage(@ModelAttribute MessageDTO messageDTO, HttpServletRequest request){
        String remoteHostAddress = request.getRemoteAddr();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((UserDetails)principal).getUsername();
        boolean isBanned = userService.isUserMarkedAsBanned(userName);

        if(userName.isEmpty() || messageDTO.getMessage().isBlank()) {
            loggerSlf4j.warn(EventID.TW0005, "Form data missing or invalid");
            return "general-error";
        }
        try {
            if (!isBanned) {
                User user = userService.getUserByLogin(userName)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));
                messageService.addMessages(messageDTO, remoteHostAddress, user);
                loggerSlf4j.info(EventID.TW0005, "Message saved for user: {}", userName);
                return "userpage";
            } else {
                loggerSlf4j.info(EventID.TW0005, "User is banned first name: {} ", userName);
                return "banned";
            }
        }catch (Exception e){
            loggerSlf4j.error("Unexpected error while adding message", e);
            return "general-error";
        }
    }

    @GetMapping("/show-messages")
    public ModelAndView readMessages() {
        loggerSlf4j.info(EventID.TW0002 + " Reading messages and logins and Display all messages. \"MessageController.class.\"");
        List<Message> messageList = messageRepository.getAllMessagesAndDetails();
        try{
            return new ModelAndView("show-messages", "messageObj", messageList);
        } catch (Exception e){
            loggerSlf4j.info(EventID.TW0004, "Read data exception or no data" + " " + e.getMessage());
            return new ModelAndView("no-messages", "messageObj", messageList);
        }
    }
}
