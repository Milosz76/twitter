package mvc.repository;

import mvc.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("SELECT m FROM Message m ORDER BY localDate DESC LIMIT 30")
    List<Message> getAllMessagesAndDetails();
}
