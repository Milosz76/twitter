package mvc.service;

import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import mvc.configuration.ApplicationConfiguration;
import mvc.configuration.SecurityConfig;
import mvc.model.EventID;
import mvc.model.dto.UserDTO;
import mvc.model.entity.ErrorType;
import mvc.model.entity.User;
import mvc.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jboss.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = Logger.getLogger(UserService.class);


    /**
     * Get all users listed from DB
     * @return registered users list
     */
    @Transactional
    public List<UserDTO> getAllUsers() {
        LOGGER.info(ErrorType.TECHNICAL + " " + EventID.TW0001 +  " Get all users listed from DB.");
        return userRepository
                .findAll()
                .stream()
                .map(user -> applicationConfiguration.modelMapper().map(user, UserDTO.class))
                .collect(Collectors.toList());
    }


    /**
     * Create new User in the Database if the e-mail in DB doesn't exist
     * @param userDTO
     * @return Object User
     */
    @Transactional
    public User create(UserDTO userDTO){
            try {
                if(userDTO != null) {
                    User user = new User();
                    user.setLogin(userDTO.getLogin());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail());
                    user.setBirthDate(userDTO.getBirthDate());
                    user.setRole(userDTO.getRole());
                    user.setUserBanned(false);
                    user.setPassword(securityConfig.passwordEncoder().encode(userDTO.getPassword()));
                    user.setRoles(Collections.singleton("USER"));
                    user.setAccountCreated(new Date());

                    LOGGER.info(ErrorType.TECHNICAL + " " + EventID.TW0006 +
                            " New User save in the DB. " + userDTO.getFirstName() + " " + userDTO.getLastName());
                    return userRepository.save(user);
                }
            }catch (PersistenceException e){
                LOGGER.info(ErrorType.PERSISTENCE_ERROR + " " + EventID.TW0007 +
                        " User didn't create in the DB: " + userDTO.getFirstName() + " " + userDTO.getLastName());
            }
        return null;
    }


    /**
     * Update User's data
     * @param userDTO
     * @return updated user data
     */
    @Transactional
    public User updateUserProfile(UserDTO userDTO) {
        Optional<User> userFound = userRepository.findById(userDTO.getId());

        if (userFound.isEmpty()) {
            LOGGER.warn("User not found with ID: " + userDTO.getId());
            return null;
        }

        User user = userFound.get();

        try {
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setBirthDate(userDTO.getBirthDate());
            user.setRole(userDTO.getRole());

            if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
                user.setPassword(securityConfig.passwordEncoder().encode(userDTO.getPassword()));
            }

            user.setAccountUpdated(new Date());

            LOGGER.info("User data updated in the DB: " + userDTO.getFirstName() + " " + userDTO.getLastName());
            return userRepository.save(user);

        } catch (PersistenceException e) {
            LOGGER.error(ErrorType.PERSISTENCE_ERROR + " " + EventID.TW0007 +
                    " User data not updated in the DB. " + userDTO.getFirstName() + " " + userDTO.getLastName(), e);
            return null;
        }
    }


    /**
     * Delete user by id
     * @param id
     */
    public void deleteUserById(Long id) {
        LOGGER.info(ErrorType.USER + " " + EventID.TW0001 +  " Delete User by ID. {} " + id);
        userRepository.removeUserById(id);
    }

    /**
     * Find user by id
     *
     * @param id
     */
    @Transactional
    public Optional<User> findByid(Long id){
        LOGGER.info(ErrorType.USER + " " + EventID.TW0001 +  " Find user by ID. {} " + id);
        return userRepository.findById(id);
    }

    /**
     * Checking if the email address exists in the Database
     * @param email
     * @return true or false
     */
    public boolean isEmailExistingInDatabase(String email){
        return userRepository.findByEmail(email).stream().findFirst().isPresent();
    }

    /**
     * Checking if login name is banned in the Database
     * @param userName
     * @return true or false
     */
    public boolean isUserMarkedAsBanned(String userName){
        return userRepository.isUserBanned(userName)
                .isPresent();
    }

    /**
     * User converter
     * @param user
     * @return converted user
     */
    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Search in the Database for the User's login name
     * @param login
     * @return founded login name
     */
    public Optional<User> getUserByLogin(String login){
        return userRepository.getUserByLogin(login);
    }
}
