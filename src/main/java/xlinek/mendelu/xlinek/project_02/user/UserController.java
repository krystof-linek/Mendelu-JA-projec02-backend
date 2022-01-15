package xlinek.mendelu.xlinek.project_02.user;

import lombok.Data;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import xlinek.mendelu.xlinek.project_02.util.GlobalResponseStatus;
import xlinek.mendelu.xlinek.project_02.util.JwtUtil;
import xlinek.mendelu.xlinek.project_02.util.NotFoundException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin()
class UserController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    //kontroler nema mit v sobe business logiku
    //prijem pozadavku a odeslani pozadavku
    //nesmiUserzadne metody a neco rozhodovat

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String welcome(){
        return "Hello user!";
    }

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception{

        User user = userService.doLogin(authRequest.getLogin());

        if (user == null) {
            throw new Exception("User not exit!");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword())
            );
        } catch (Exception e){
            throw new Exception("invalid login or password");
        }
        return jwtUtil.generateToken(user.getId(), user.getLogin(), user.getName(), user.getSurname());
        //return jwtUtil.generateToken(1, authRequest.getLogin(), "name", "sur");
    }

    @GetMapping("/users")
    List<User> getUsers(){

        return userService.getAllUsers();
    }

    @Data
    static class GetUserResponse {
        private String login;
        private String email;
        private String name;
        private String surname;
        private String gender;
        private Long registered;
    }

    @GetMapping("/user/by/id/{id}")
    UserResponseForm getUserById(@PathVariable(value="id") int id){

        UserResponseForm user = userService.getUserById(id);


        if (user == null)
            return null; //nexistuje

        return user;
    }

    @GetMapping("/user/by/login/{login}")
    UserResponseForm getUserByLogin(@PathVariable(value="login") String login){
    try {
        UserResponseForm user = userService.getUserByLogin(login);

        if (user == null) {
            new GlobalResponseStatus().handleConflict();
            throw new ResponseStatusException(NOT_FOUND, "User not found!");
        }

        return user;
    }catch (Exception e){
        System.out.println(e.getMessage());
    }
    return null;
    }

    @Data
    static class PostUserRequest {
        private String login;
        private String email;
        private String password;
        private String name;
        private String surname;
        private String gender;
    }

    @Data
    static class deleteUserRequest {
        private int id;
    }


/*
    @GetMapping("/{id}")
    Country getCountryById(@PathVariable Long id){
        return countryRepository.findById(id).orElseThrow(NotFoundException::new);
    } //optional<Country> je kvuli tomu, kdyz neco muze byt null
*/

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    User postNewUser(@RequestBody PostUserRequest request){
        return userService.newUser(
                request.login,
                request.email,
                request.password,
                request.name,
                request.surname,
                request.gender
        );
    }
/*
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.CREATED)
    List<User> postDeleteUser(@RequestBody deleteUserRequest request){
        userService.deleteUser(request.id);

        List<User> users = userRepository.findAll();

        return users;
    }

*/
}
