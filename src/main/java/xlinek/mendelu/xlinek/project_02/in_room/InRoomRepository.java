package xlinek.mendelu.xlinek.project_02.in_room;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xlinek.mendelu.xlinek.project_02.room.Room;

import java.util.List;


@Repository
public interface InRoomRepository extends JpaRepository<InRoom, PrimaryKey> {
    List<InRoom> findAll();
    List<InRoom> findAllByIdRoomsEquals(int id);
    List<InRoom> findAllByIdUsers(int id);
}
