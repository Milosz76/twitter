package mvc.controller;

import mvc.model.dto.UserDTO;
import mvc.model.entity.User;
import mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;



    @GetMapping("/finduser")
    public ModelAndView findUser() {
        logger.warn("List the users");
        return new ModelAndView("finduser", "finduser", new UserDTO());
    }

    @GetMapping("/finduserbylastname")
    public ModelAndView findUserByLastName(@ModelAttribute UserDTO userDTO) {
        logger.warn("Find a user by a last name");
        List<User> userDTOList = userService.findUserByLastName(userDTO.getLastName());
        return new ModelAndView("users", "userList", userDTOList);
    }

    @GetMapping("/finduserbyemail")
    public ModelAndView findUserByEmailAddress(@ModelAttribute UserDTO userDTO){
        logger.warn("Find a user by the e-mail address");
        String email = userDTO.getMail();
        List<User> list = userService.isEmailRegisteredInDatabase(email);
        return new ModelAndView("users", "userList", list);
    }

    @GetMapping("/users")
    ResponseEntity<List<UserDTO>> findAllUsers() {
        logger.warn("Exposing all users!");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<Optional<User>> findByUserID(@PathParam("id") Long id){
        logger.warn("Exposing specific user!");
        Optional<User> result = userService.findByid(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/users")
    @PreAuthorize("@securityService.hasPermission({'USER_ADD', 'ADMIN_ADD'})")
    ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        logger.info("Creating the new user " + userDTO);
        User result = userService.create(userDTO);
        return ResponseEntity.created(URI.create("/" + result.getId())).build();
    }
}
