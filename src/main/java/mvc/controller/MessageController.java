package mvc.controller;

import mvc.model.EventID;
import mvc.model.dto.DeviceInformationDTO;
import mvc.model.dto.MessageDTO;
import mvc.model.dto.UserDTO;
import mvc.service.DeviceInformationService;
import mvc.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MessageController {

    private static final Logger loggerSlf4j = LoggerFactory.getLogger(MessageController.class);
    private MessageService messageService;
    private MessageDTO messageDTO;
    private UserDTO userDTO;
    @Autowired
    private DeviceInformationService deviceInformationService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/useraddcontent")
    public ModelAndView getAddNewMessage(){
        loggerSlf4j.warn("Before add a new message");
        return new ModelAndView("useraddcontent", "userAddMessage", new MessageDTO());
    }

    @PostMapping("/useraddcontent")
    public String addNewMessage(@ModelAttribute MessageDTO messageDTO){
        loggerSlf4j.warn("Creating a new message");
        System.out.println(messageDTO.getMessage());

        messageService.addMessages(messageDTO);
        return "redirect:/userpage";
    }

    @GetMapping("/show-messages")
    public ModelAndView readMessages(HttpServletRequest request){
        loggerSlf4j.warn(EventID.TW0002, "Reading messages and logins and Display all messages. \"MessageController.class.\"");

        String hostname = request.getRemoteHost();
        DeviceInformationDTO deviceInformationDTO = new DeviceInformationDTO();
        deviceInformationDTO.setName(hostname);
        deviceInformationService.save(deviceInformationDTO);

//poprawic za pomoca jednej query i Entity Manager
        try {
            ModelAndView model = new ModelAndView("show-messages");
            model.addObject("messages", messageService.getAllMessages());
            return model;
        }catch (Exception e){
            ModelAndView modelError = new ModelAndView("show-messages");
            modelError.addObject("No massages found ", e.getMessage());
           return modelError;
        }
    }
}
