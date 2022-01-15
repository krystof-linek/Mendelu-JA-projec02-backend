package xlinek.mendelu.xlinek.project_02.in_room;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@IdClass(PrimaryKey.class)
class InRoom {
    @Id
    @Column(name = "id_users")
    private int idUsers;
    @Id
    @Column(name = "id_rooms")
    private int idRooms;
    @Column(name = "laste_meassage")
    private Long last_message;
    @Column(name = "entered")
    private Long entered;

    public InRoom(int id_users, int id_rooms, Long last_message, Long entered) {
        this.idUsers = id_users;
        this.idRooms = id_rooms;
        this.last_message = last_message;
        this.entered = entered;
    }

    @Override
    public String toString() {
        return "InRoom{" +
                "id_users=" + idUsers +
                ", id_rooms=" + idRooms +
                ", last_message=" + last_message +
                ", entered=" + entered +
                '}';
    }
}
