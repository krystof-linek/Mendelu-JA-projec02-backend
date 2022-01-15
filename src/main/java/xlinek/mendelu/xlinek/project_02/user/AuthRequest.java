package xlinek.mendelu.xlinek.project_02.user;

import lombok.*;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString

public class AuthRequest {
    private String login;
    private String password;
}


