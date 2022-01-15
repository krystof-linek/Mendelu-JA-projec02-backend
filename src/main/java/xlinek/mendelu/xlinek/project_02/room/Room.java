package xlinek.mendelu.xlinek.project_02.room;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xlinek.mendelu.xlinek.project_02.user.User;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RoomSeq")
    @Column(name = "id_rooms")
    private int id;
    @Column(name = "created")
    private Long created;
    @Column(name = "title")
    private String title;
    @ManyToOne
    @JoinColumn(name = "id_users_owner", foreignKey = @ForeignKey(name="FK_USER"))
    private User user;
    @Column(name = "lock")
    private String lock;

    public Room(Long created, String title, User user, String lock) {
        this.created = created;
        this.title = title;
        this.user = user;
        this.lock = lock;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id_rooms=" + id +
                ", created=" + created +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", lock='" + lock + '\'' +
                '}';
    }
}
