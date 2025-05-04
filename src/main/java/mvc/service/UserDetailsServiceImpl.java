package mvc.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvc.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.info("Log info for method loadUserByUsername in class UserDetailsServiceImpl");

        mvc.model.entity.User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Login name not found"));

        log.info("Found user: " + user.getLogin());
        log.info("Hashed password from database: " + user.getPassword());
        log.info("Roles from database: " + user.getRoles());

        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}
