package mvc.controller;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import mvc.configuration.SecurityConfig;
import mvc.model.dto.UserDTO;
import mvc.model.entity.User;
import mvc.repository.UserRepository;
import mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    public UserController(UserService userService, UserRepository userRepository, SecurityConfig securityConfig) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    @GetMapping("/userpage")
    public ModelAndView userPage() {
        logger.info("Page loaded - userpage.html");
        return new ModelAndView("userpage");
    }

    @GetMapping("/finduser")
    public ModelAndView findUser() {
        logger.warn("List the users");
        return new ModelAndView("finduser", "finduser", new UserDTO());
    }

    @GetMapping("/finduserbylastname")
    public ModelAndView findUserByLastName(@ModelAttribute UserDTO userDTO) {
        logger.warn("Find a user by a last name");
        List<User> userDTOList = userRepository.findUserByLastName(userDTO.getLastName());
        return new ModelAndView("users", "userList", userDTOList);
    }

    @GetMapping("/findemail")
    public ModelAndView getFindEmailPage() {
        logger.warn("Displaying find user by email form");
        return new ModelAndView("email", "userDTO", new UserDTO());
    }

    @GetMapping("/finduserbyemail")
    public ModelAndView findUserByEmail(@ModelAttribute UserDTO userDTO) {
        logger.warn("Finding user by e-mail address: {}", userDTO.getEmail());

        List<User> foundUsers = userRepository.findByEmail(userDTO.getEmail());
        User foundUser = foundUsers.isEmpty() ? null : foundUsers.get(0);

        ModelAndView modelAndView = new ModelAndView("email");
        modelAndView.addObject("user", foundUser);
        return modelAndView;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        logger.warn("Exposing all users!");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<Optional<User>> findByUserID(@PathParam("id") Long id) {
        logger.warn("Exposing specific user!");
        Optional<User> result = userService.findByid(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        logger.info("Removed user id {}", id);
        userService.deleteUserById(id);
        return "redirect:/login";
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        logger.info("Creating the new user " + userDTO);

        if(userDTO.getPassword() == null){
            throw new IllegalArgumentException("Password is null!");
        }
        User result = userService.create(userDTO);
        return ResponseEntity.created(URI.create("/" + result.getId())).build();
    }

    @GetMapping("/edit")
    public String showEditForm(Model model, Principal principal) {
        String userName = principal.getName();
        Optional<User> userLogin =  userRepository.getUserByLogin(userName);

        if (userLogin.isPresent()) {
            UserDTO userDTO = userService.convertToDTO(userLogin.get());
            model.addAttribute("user", userDTO);
            return "edit";
        } else {
            logger.warn("User with user name {} not found for editing", userLogin);
            return "redirect:/userpage";
        }
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid UserDTO userDTO,
                             @RequestParam("confirmPassword") String confirmPassword,
                             BindingResult result,
                             Model model) {

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            if (!userDTO.getPassword().equals(confirmPassword)) {
                result.rejectValue("password", "error.user", "Passwords do not match.");
            }
        } else {
            Optional<User> existing = userService.findByid(userDTO.getId());
            existing.ifPresent(user -> userDTO.setPassword(user.getPassword()));
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "edit";
        }

        userService.updateUserProfile(userDTO);
        return "redirect:/edit?id=" + userDTO.getId();
    }
}
