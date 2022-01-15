package xlinek.mendelu.xlinek.project_02.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xlinek.mendelu.xlinek.project_02.in_room.InRoomRepository;
import xlinek.mendelu.xlinek.project_02.messages.MessageRepository;
import xlinek.mendelu.xlinek.project_02.user.UserRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
class RoomService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private InRoomRepository inRoomRepository;
    @Autowired
    private MessageRepository messageRepository;

    protected List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    protected RoomController.GetRoomResponse newRoom(String title, int id_users_owner, String lock) {
        var room = new Room();
        room.setCreated(new Timestamp(System.currentTimeMillis()).getTime());
        room.setTitle(title);
        room.setUser(userRepository.findUserById(id_users_owner));
        room.setLock(lock);

        roomRepository.save(room);

        RoomController.GetRoomResponse record = new RoomController.GetRoomResponse();

        record.setId_rooms(room.getId());
        record.setTitle(room.getTitle());
        if (room.getUser() != null) {
            record.setId_users_owner(room.getUser().getId());
            record.setName(room.getUser().getName());
            record.setSurname(room.getUser().getSurname());
        }
        record.setLock(room.getLock());

        return record;
    }

    protected Room getRoomById(int id) {
        return roomRepository.findRoomById(id);
    }

    protected void setRoomLock(String value, int id_rooms) {
        roomRepository.findRoomById(id_rooms).setLock(value);
        roomRepository.flush();
    }

    protected void deleteRoomById(int id) {
        inRoomRepository.deleteAll(inRoomRepository.findAllByIdRoomsEquals(id));
        messageRepository.deleteAll(messageRepository.findAllByRoom(roomRepository.findRoomById(id)));
        roomRepository.delete(roomRepository.findRoomById(id));
    }
}
