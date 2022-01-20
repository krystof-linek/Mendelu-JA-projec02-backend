package xlinek.mendelu.xlinek.project_02.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userRepo.findUserByLogin(login);

        if(user != null)
            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), new ArrayList<>());

        return null;
    }

    User doLogin(String login){

        User user = userRepo.findUserByLogin(login);

        if (user == null)
            return null;

        return user;
    }

    User newUser(String login, String email, String password, String name, String surname, String gender){
        var user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setGender(gender);
        user.setRegistered(new Timestamp(System.currentTimeMillis()).getTime());
        user.setRole("user");
        user.setActive("true");

        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        List<UserResponseForm> users = new ArrayList<>();

        for (User u : userRepo.findAll()) {
            users.add(new UserResponseForm(
                    u.getLogin(), u.getEmail(), u.getName(),
                    u.getSurname(), u.getGender(), u.getRegistered()
                    ));
        }

        return userRepo.findAll();
    }

    private UserResponseForm getSafeUser(User user){

        if (user == null)
            return null;

        return new UserResponseForm(
                user.getLogin(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getGender(),
                user.getRegistered()
        );
    }

    void deleteUser(int id){

        userRepo.delete(userRepo.findUserById(id));
    }

    public UserResponseForm getUserById(int id) {
        return new UserService().getSafeUser(userRepo.findUserById(id));
    }

    public UserResponseForm getUserByLogin(String login) {
        return new UserService().getSafeUser(userRepo.findUserByLogin(login));
    }
}
