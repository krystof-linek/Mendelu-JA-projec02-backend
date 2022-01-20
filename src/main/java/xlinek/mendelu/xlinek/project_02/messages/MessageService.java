package xlinek.mendelu.xlinek.project_02.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xlinek.mendelu.xlinek.project_02.room.RoomRepository;
import xlinek.mendelu.xlinek.project_02.user.UserRepository;
import xlinek.mendelu.xlinek.project_02.util.JwtUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
class MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;


    protected List<MessageController.MessageData> getAllMessagesInRoom(int id_rooms) {

        List<Message> messages = messageRepository.findAllByRoom(roomRepository.findRoomById(id_rooms));

        List<MessageController.MessageData> records = new ArrayList<>();

        if (messages.size() != 0){
            for (Message m : messages){
                MessageController.MessageData record = transformMessage(m);
                records.add(record);
            }

        }

        return records;
    }

    protected MessageController.MessageData sendMessage(int id_rooms, String header, MessageController.PostMessageRequest request) {
        String token = header.substring(7);

        int sender = (int)(jwtUtil.getAllDataFromToken(token).get("userId"));

        Message message = new Message();

        message.setMessage(request.getMessage());
        message.setRoom(roomRepository.findRoomById(id_rooms));
        message.setUser_from(userRepository.findUserById(sender));
        message.setCreated(new Timestamp(System.currentTimeMillis()).getTime());

        messageRepository.save(message);

        return  transformMessage(messageRepository.save(message));
    }

    private MessageController.MessageData transformMessage (Message message) {
        MessageController.MessageData messageData = new MessageController.MessageData();

        messageData.setId_users_from(message.getUser_from().getId());
        messageData.setName(message.getUser_from().getName());
        messageData.setSurname(message.getUser_from().getSurname());
        messageData.setMessage(message.getMessage());
        messageData.setCreated(message.getCreated());

        return messageData;
    }
}
