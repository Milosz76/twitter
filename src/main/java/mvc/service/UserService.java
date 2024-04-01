package mvc.service;

import mvc.model.EventID;
import mvc.model.dto.UserDTO;
import mvc.model.entity.ErrorType;
import mvc.model.entity.User;
import mvc.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jboss.logging.Logger;

@Service
public class UserService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    public UserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    /**
     * Get all users listed from DB
     * @return registered users list
     */
    public List<UserDTO> getAllUsers() {
        LOGGER.info(ErrorType.TECHNICAL + " " + EventID.TW0001 +  " Get all users listed from DB.");
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Find user by his/her lastName
     * @param lastName
     * @return User
     */
    public List<User> findUserByLastName(String lastName) {
        LOGGER.info(ErrorType.TECHNICAL + " " + EventID.TW0001 +  " Find user by lastname -" + lastName);
        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER_BY_LASTNAME, User.class);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }


    /**
     * Create new User in the Database if the e-mail in DB doesn't exist
     * @param userDTO
     * @return Object User
     */
    public User create(UserDTO userDTO){
            User user = modelMapper.map(userDTO, User.class);
            LOGGER.info(ErrorType.TECHNICAL + " " + EventID.TW0003 +
                    " New User save in the DB. " + userDTO.getFirstName() + " " + userDTO.getLastName());
            return userRepository.save(user);
    }


    /**
     * Delete user by id
     * @param id
     */
    public void deleteUserById(Long id) {
        LOGGER.info(ErrorType.USER + " " + EventID.TW0001 +  " Delete User by ID.");
        userRepository.deleteUserById(id);
    }

    /**
     * Find user by id
     *
     * @param id
     */
    public Optional<User> findByid(Long id){
        LOGGER.info(ErrorType.USER + " " + EventID.TW0001 +  " Find user by ID.");
        return userRepository.findById(id);
    }


    /**
     * Searching for the specific email in the Database for the email address in that case the registered one
     * @param email
     * @return an email address (plus user with other details)
     */
    public List<User> isEmailRegisteredInDatabase(String email){
        TypedQuery<User> query = entityManager.createNamedQuery(User.USER_IS_EMAIL_REGISTERED, User.class);
        query.setParameter("email", email);
        return query.getResultList();
    }

    /**
     * Checking if the email address exists in the Database
     * @param email
     * @return true or false
     */
    public boolean isEmailExistingInDatabase(String email){
        return isEmailRegisteredInDatabase(email).stream().findFirst().isPresent();
    }
}
