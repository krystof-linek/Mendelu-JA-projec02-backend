package xlinek.mendelu.xlinek.project_02.user;


import lombok.*;

@Data
class UserResponseForm {
    private String login;
    private String email;
    private String name;
    private String surname;
    private String gender;
    private Long registered;

    public UserResponseForm(String login, String email, String name, String surname, String gender, Long registered) {
        this.login = login;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.registered = registered;
    }
}
