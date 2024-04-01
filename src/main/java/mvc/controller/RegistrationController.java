package mvc.controller;

import mvc.model.dto.UserDTO;
import mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        String email = userDTO.getMail();
        boolean eMailAddress = userService.isEmailExistingInDatabase(email);
        if(!eMailAddress){
            userService.create(userDTO);
            return "successful-registration";
        } else {
            return "failed-registration";
        }
    }
}
