package xlinek.mendelu.xlinek.project_02.messages;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xlinek.mendelu.xlinek.project_02.room.Room;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByRoom(Room room);
}
