package mvc.controller;

import lombok.extern.slf4j.Slf4j;
import mvc.model.dto.UserDTO;
import mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    String showRegistration(Model model){
        model.addAttribute("user", new UserDTO());
        return "registration";
    }

    @PostMapping("/registration")
    String addUser(@ModelAttribute UserDTO userDTO){
        log.info("Registration Controller " + userDTO.getLogin() + " " + userDTO.getEmail());
        String email = userDTO.getEmail();
        boolean eMailAddress = userService.isEmailExistingInDatabase(email);
        if(!eMailAddress){
            userService.create(userDTO);
            return "successful-registration";
        } else {
            return "failed-registration";
        }
    }
}
