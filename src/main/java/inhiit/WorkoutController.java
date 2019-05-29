package inhiit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    // get all workouts
    @GetMapping("/workouts")
    public Iterable<Workout> getWorkouts() {
        return workoutRepository.findAll();
    }

    // create new workout
    @PostMapping("/workouts")
    public Workout newWorkout(@RequestBody Workout workout, HttpSession session) throws Exception{
        User user = userRepository.findByUsername(session.getAttribute("username").toString());
        if(user == null){
            throw new Exception("you must be logged in");
        }
        workout.setUser(user);
        Workout newWorkout = workoutRepository.save(workout);
        return newWorkout;
    }

    // deletes workouts
    @DeleteMapping("/workouts/{id}")
    public String deleteWorkout(@PathVariable("id") Long id) {
        workoutRepository.deleteById(id);
        return "deleted " + id;
    }

    @PutMapping("/workouts/{id}/edit")
    public Workout editWorkout(@PathVariable("id") Long id, @RequestBody Workout formData) throws Exception {
        Optional<Workout> response = workoutRepository.findById(id);
        if (response.isPresent()) {

            Workout workout = response.get();
            if (formData.getName() != null) {
                workout.setName(formData.getName());
            }
            if (formData.getIntervalone() != 0) {
                workout.setIntervalone(formData.getIntervalone());
            }
            if (formData.getIntervaltwo() != 0) {
                workout.setIntervaltwo(formData.getIntervaltwo());
            }
            if (formData.getCycles() != 0) {
                workout.setCycles(formData.getCycles());
            }

            if (formData.getReps() != 0) {
                workout.setReps(formData.getReps());
            }


            return workoutRepository.save(workout);

        }
        throw new Exception("No workouts found found");
    }

    //show one workout
    @GetMapping("/workouts/{id}")
    public Workout showOne (@PathVariable("id") Long id) throws Exception {
        Optional<Workout> response = workoutRepository.findById(id);
        if (response.isPresent()) {

            return response.get();
        }
        throw new Exception("no post found");
    }

}
