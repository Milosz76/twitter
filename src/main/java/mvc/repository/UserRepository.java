package mvc.repository;

import mvc.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findUserByLastName(@Param("lastName") String lastName);

    List<User> findUserByEmail(@Param("email") String email);

    List<User> findAll();

    User save(User user);

    void deleteUserById(Long id);
}
