package xlinek.mendelu.xlinek.project_02.messages;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xlinek.mendelu.xlinek.project_02.room.Room;
import xlinek.mendelu.xlinek.project_02.user.User;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MessSeq")
    @Column(name = "id_messages")
    private int id_messages;
    @ManyToOne
    @JoinColumn(name = "id_rooms", foreignKey = @ForeignKey(name="FK_ROOM"))
    private Room room;
    @ManyToOne
    @JoinColumn(name = "id_users_from", foreignKey = @ForeignKey(name="FK_USER_FROM"))
    private User user_from;
    @ManyToOne
    @JoinColumn(name = "id_users_to", foreignKey = @ForeignKey(name="FK_USER_TO"))
    private User user_to;
    @Column(name = "created")
    private Long created;
    @Column(name = "message")
    private String message;

    public Message(Room room, User user_from, User user_to, Long created, String message) {
        this.room = room;
        this.user_from = user_from;
        this.user_to = user_to;
        this.created = created;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id_messages=" + id_messages +
                ", room=" + room +
                ", user_from=" + user_from +
                ", user_to=" + user_to +
                ", created=" + created +
                ", message='" + message + '\'' +
                '}';
    }
}
