package mvc.repository;

import mvc.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAll();

    User save(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id =:id")
    void removeUserById(Long id);

    Optional<User> findUserByLogin(String login);

    @Query("SELECT u FROM User u WHERE u.lastName LIKE %?1%")
    List<User> findUserByLastName(@Param("lastName") String lastName);

    @Query("SELECT u FROM User u WHERE u.email =:email")
    List<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.login =:login")
    Optional<User> getUserByLogin(@Param("login") String login);

    @Query("SELECT u FROM User u WHERE u.login =:login AND u.isUserBanned=true")
    Optional<User> isUserBanned(@Param("login") String login);
}
