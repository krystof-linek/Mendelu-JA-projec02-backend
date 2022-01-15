package xlinek.mendelu.xlinek.project_02.in_room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xlinek.mendelu.xlinek.project_02.room.Room;
import xlinek.mendelu.xlinek.project_02.room.RoomRepository;
import xlinek.mendelu.xlinek.project_02.user.User;
import xlinek.mendelu.xlinek.project_02.user.UserRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
class InRoomService {

    @Autowired
    private InRoomRepository inRoomRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    protected InRoom joinUserToRoom(int id_users, int id_rooms) {

        Room room = getRoomById(id_rooms);

        if (room.getLock().equals("true")){

            return null;

        } else {

            var inRoom = new InRoom();
            inRoom.setIdUsers(id_users);
            inRoom.setIdRooms(id_rooms);
            inRoom.setLast_message(null);
            inRoom.setEntered(new Timestamp(System.currentTimeMillis()).getTime());

            return inRoomRepository.save(inRoom);
        }
    }

    protected Room getRoomById(int id) {
        return roomRepository.findRoomById(id);
    }

    protected List<InRoomController.GetCountUsers> getAllUsersInRooms() {
        List<Room> rooms = roomRepository.findAll();

        List<InRoomController.GetCountUsers> records = new ArrayList<>();

        for (Room r : rooms){
            var record = new InRoomController.GetCountUsers();
            record.setId_rooms(r.getId());
            record.setCount(inRoomRepository.findAllByIdRoomsEquals(r.getId()).size());
            records.add(record);
        }

        return records;

    }

    protected List<InRoomController.GetSafeUser> getAllUsersInRoom(int id) {

        List<InRoom> inRooms = inRoomRepository.findAllByIdRoomsEquals(id);

        List<InRoomController.GetSafeUser> safeUsers = new ArrayList<>();

        if (inRooms.size() == 0)
            return safeUsers; //vraci prazdne pole

        for (InRoom ir : inRoomRepository.findAllByIdRoomsEquals(id)){

            User user = userRepository.findUserById(ir.getIdUsers());

            if (user != null) {
                InRoomController.GetSafeUser safeU = new InRoomController.GetSafeUser();

                safeU.setId_users(user.getId());
                safeU.setLogin(user.getLogin());
                safeU.setEmail(user.getEmail());
                safeU.setName(user.getName());
                safeU.setSurname(user.getSurname());
                safeU.setGender(user.getGender());
                safeU.setRegistered(user.getRegistered());
                safeU.setRole(user.getGender());
                safeU.setActive(user.getActive());

                safeUsers.add(safeU);
            }

        }

        return safeUsers;
    }

    protected void leaveUserFromRoom(int id_users, int id_rooms) {
        inRoomRepository.delete(inRoomRepository.findById(new PrimaryKey(id_users, id_rooms)).get());
    }

    protected List<InRoomController.GetRoomResponse> getRoomsWhereIsUser(int id_users) {
        List<InRoomController.GetRoomResponse> results = new ArrayList<>();

        List<InRoom> inRooms = inRoomRepository.findAllByIdUsers(id_users);

        if (inRooms.size() != 0) {

            for (InRoom ir : inRooms){
                Room room = roomRepository.findRoomById(ir.getIdRooms());

                InRoomController.GetRoomResponse record = new InRoomController.GetRoomResponse();

                record.setId_rooms(room.getId());
                record.setTitle(room.getTitle());
                record.setLock(room.getLock());

                if (room.getUser() != null){
                    record.setId_users_owner(room.getUser().getId());
                    record.setName(room.getUser().getName());
                    record.setSurname(room.getUser().getSurname());
                    record.setGender(room.getUser().getGender());

                    results.add(record);
                }

            }

        }
        return results;
    }
}
