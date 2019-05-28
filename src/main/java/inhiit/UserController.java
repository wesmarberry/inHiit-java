package inhiit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

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

    // logs out users
    @GetMapping("/users/logout")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "successfully logged out";
    }

    @PostMapping("/users/login")
    public User login(@RequestBody User login, HttpSession session) throws IOException {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByUsername(login.getUsername());
        if(user ==  null){
            throw new IOException("Invalid Credentials");
        }
        boolean valid = bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword());
        if(valid){
            session.setAttribute("username", user.getUsername());
            return user;
        }else{
            throw new IOException("Invalid Credentials");
        }
    }

    // deletes users
    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "deleted " + id;
    }

    // edit users
    @PutMapping("/users/{id}/edit")
    public User editUser(@PathVariable("id") Long id, @RequestBody User formData) throws Exception {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {

            User user = response.get();
            user.setUsername(formData.getUsername());
            user.setZipcode(formData.getZipcode());
            return userRepository.save(user);
        }
        throw new Exception("no user to update");

    }

    // shows single user with all workouts populated
    @GetMapping("/users/{id}")
    public HashMap<String, Object> findUser(@PathVariable("id") Long id)throws Exception{
        Optional<User> response = userRepository.findById(id);
        if(response.isPresent()){
            User user = response.get();
            Iterable<Workout> workouts = workoutRepository.findByUser(user);
            HashMap<String, Object> result = new HashMap<>();
            result.put("user", user);
            result.put("workouts", workouts);
            return result;
        }
        throw new Exception("no such user");
    }



}
