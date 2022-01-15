package xlinek.mendelu.xlinek.project_02.in_room;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor @AllArgsConstructor
public class PrimaryKey implements Serializable {
    private int idUsers;
    private int idRooms;
}


