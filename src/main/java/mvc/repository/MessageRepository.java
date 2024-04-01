package mvc.repository;

import mvc.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    //SQL for massages, dates and users
    //create methods

}
