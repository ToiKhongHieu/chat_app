package thi.nguyen.whats_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thi.nguyen.whats_app.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {

    @Query("select m from Message m where m.chat.id = :chatId")
    public List<Message> findByChatId(@Param("chatId") Integer chatId);
}
