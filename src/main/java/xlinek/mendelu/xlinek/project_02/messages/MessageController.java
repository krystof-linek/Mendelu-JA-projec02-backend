package xlinek.mendelu.xlinek.project_02.messages;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin()
class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/auth/room/{id_rooms}/messages")
    public List<MessageData> getAllMessagesInRoom(@PathVariable(value="id_rooms") int id_rooms){
        return messageService.getAllMessagesInRoom(id_rooms);
    }

    @Data
    static class PostMessageRequest {
        private String message;
    }

    @Data
    static class MessageData {
        private int id_users_from;
        private String login;
        private String name;
        private String surname;
        private Long created;
        private String message;
    }

    @PostMapping("/auth/user/send/message/room/{id_rooms}")
    public MessageData sendMessage(
            @PathVariable(value="id_rooms") int id_rooms,
            @RequestHeader("authorization") String header,
            @RequestBody PostMessageRequest request
    ){
        return messageService.sendMessage(id_rooms, header ,request);
    }

}
