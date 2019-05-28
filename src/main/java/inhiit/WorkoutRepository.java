package inhiit;

import org.springframework.data.repository.CrudRepository;

public interface WorkoutRepository extends CrudRepository<Workout, Long> {
    Iterable<Workout> findByUser(User user);
}