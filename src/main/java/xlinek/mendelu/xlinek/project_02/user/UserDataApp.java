package xlinek.mendelu.xlinek.project_02.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


public class UserDataApp {

    @Bean
    public CommandLineRunner run(UserRepository repository) {
        return (args -> {
            //insertJavaAdvocates(repository);
            //System.out.println(repository.findAll());

            //repository.deleteAll();

            //System.out.println(repository.findCountryByCountryNameContaining("I"));
            //System.out.println(repository.findCountryByIdEquals(2l)); //vyber prodle ID funguje
        });
    }


/*
    private void insertJavaAdvocates(UserRepository repository) {
        repository.save(new User("xlinek", "Krystof", "Linek", "heslo"));
        repository.save(new User("xstepa", "Lenka", "Stepankova", "lenice"));

        //User test = new User"Test", "test", null);
        //repository.save(test);
        //repository.save(new User("Ma", "Potomka", test));
    }

 */
}
