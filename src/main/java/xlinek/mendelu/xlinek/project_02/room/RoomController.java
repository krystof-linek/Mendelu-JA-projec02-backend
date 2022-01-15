package xlinek.mendelu.xlinek.project_02.room;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import xlinek.mendelu.xlinek.project_02.user.*;
import xlinek.mendelu.xlinek.project_02.util.GlobalResponseStatus;
import xlinek.mendelu.xlinek.project_02.util.JwtUtil;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

@RestController
@CrossOrigin()
class RoomController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RoomService roomService;

    //kontroler nema mit v sobe business logiku
    //prijem pozadavku a odeslani pozadavku
    //nesmiUserzadne metody a neco rozhodovat

    @Data
    static class GetRoomResponse{
        private int id_rooms;
        private String title;
        private int id_users_owner;
        private String lock;
        private String name;
        private String surname;
    }

    @GetMapping("/auth/rooms")
    public List<GetRoomResponse> getAllRooms(){
        List<GetRoomResponse> rooms = new ArrayList<>();

        for (Room r : roomService.getAllRooms()){
            var record = new GetRoomResponse();

            record.setId_rooms(r.getId());
            record.setTitle(r.getTitle());
            if (r.getUser() != null) {
                record.setId_users_owner(r.getUser().getId());
                record.setName(r.getUser().getName());
                record.setSurname(r.getUser().getSurname());
            }
            record.setLock(r.getLock());

            rooms.add(record);

        }

        return rooms;
    }

    @GetMapping("/auth/room/{id}")
    GetRoomResponse getRoomById(@PathVariable(value="id") int id){

        Room room = roomService.getRoomById(id);

        if (room == null)
            return null; //nexistuje

        GetRoomResponse record = new GetRoomResponse();

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

    @Data
    static class PostRoomRequest{
        private String title;
        private String lock;
    }

    @PostMapping("/auth/room/new")
    @ResponseStatus(HttpStatus.CREATED)
    GetRoomResponse postNewRoom(@RequestBody RoomController.PostRoomRequest request, @RequestHeader("authorization") String header){

        String token = header.substring(7);

        int owner = (int)(jwtUtil.getAllDataFromToken(token).get("userId"));

        return roomService.newRoom(
                request.title,
                owner,
                request.lock
        );
    }

    @GetMapping("/auth/room/lock/{id}")
    @ResponseStatus(HttpStatus.OK)
    void setRoomLock(@PathVariable(value="id") int id){
        roomService.setRoomLock("true", id);
    }

    @GetMapping("/auth/room/unlock/{id}")
    @ResponseStatus(HttpStatus.OK)
    void setRoomUnlock(@PathVariable(value="id") int id){
        roomService.setRoomLock("false", id);
    }

    @DeleteMapping("/auth/room/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void deleteRoomById(@PathVariable(value="id") int id){
        roomService.deleteRoomById(id);
    }

}
