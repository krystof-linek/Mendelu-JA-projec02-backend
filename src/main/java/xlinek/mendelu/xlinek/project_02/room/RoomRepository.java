package xlinek.mendelu.xlinek.project_02.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAll();
    Room findRoomById(int id);
}
