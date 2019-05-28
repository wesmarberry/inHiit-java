package inhiit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private UserService userService;

    //at the top of the class definition near userRepository and userService:
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/say")
    public String sayHello() {
        return "hello";
    }

    // get all users route
    @GetMapping("/users")
    public Iterable<User> getUser() {
        return userRepository.findAll();
    }

    // register route (create new users)
    @PostMapping("/users")
    public User createUser(@RequestBody User user, HttpSession session){
        User newUser = userService.saveUser(user);
        if(newUser != null){
            session.setAttribute("userName", newUser.getUsername());
            session.setAttribute("userDbId", newUser.getId());
            session.setAttribute("logged", true);
            session.setAttribute("msg", "login successful");
        }
        return newUser;
    }

}
