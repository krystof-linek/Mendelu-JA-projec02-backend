package xlinek.mendelu.xlinek.project_02.in_room;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xlinek.mendelu.xlinek.project_02.room.Room;
import xlinek.mendelu.xlinek.project_02.user.User;
import xlinek.mendelu.xlinek.project_02.util.JwtUtil;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin()
public class InRoomController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private InRoomService inRoomService;


    @Data
    static class GetCountUsers{
        private int id_rooms;
        private int count;
    }

    @GetMapping("/users/rooms")
    List<GetCountUsers> getAllUsersInRooms(){

        List<GetCountUsers> users = inRoomService.getAllUsersInRooms();


        if (users == null)
            return null; //nexistuje

        return users;
    }

    @Data
    static class GetSafeUser{
        private int id_users;
        private String login;
        private String email;
        private String name;
        private String surname;
        private String gender;
        private Long registered;
        private String role;
        private String active;
    }

    @GetMapping("/users/room/{id}")
    List<GetSafeUser> getAllUsersInRoomById(@PathVariable(value="id") int id){

        return inRoomService.getAllUsersInRoom(id);
    }

    @GetMapping("/auth/user/join/room/{id_rooms}")
    @ResponseStatus(HttpStatus.OK)
    InRoom joinUserToRoom(@PathVariable(value="id_rooms") int id_rooms, @RequestHeader("authorization") String header){

        String token = header.substring(7);

        int id_users = (int)(jwtUtil.getAllDataFromToken(token).get("userId"));

        return inRoomService.joinUserToRoom(
                id_users,
                id_rooms
        );
    }

    @Data
    static class GetRoomResponse{
        private int id_rooms;
        private String title;
        private int id_users_owner;
        private String lock;
        private String name;
        private String surname;
        private String gender;
    }

    @GetMapping("/auth/user/in/rooms")
    @ResponseStatus(HttpStatus.OK)
    List<GetRoomResponse> getRoomsWhereIsUser(@RequestHeader("authorization") String header){

        String token = header.substring(7);

        int id_users = (int)(jwtUtil.getAllDataFromToken(token).get("userId"));

        return inRoomService.getRoomsWhereIsUser(id_users);
    }

    @DeleteMapping ("/auth/user/leave/room/{id_rooms}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void leaveUserToRoom(@PathVariable(value="id_rooms") int id_rooms, @RequestHeader("authorization") String header){

        String token = header.substring(7);

        int id_users = (int)(jwtUtil.getAllDataFromToken(token).get("userId"));

        inRoomService.leaveUserFromRoom(id_users, id_rooms);
    }

}
