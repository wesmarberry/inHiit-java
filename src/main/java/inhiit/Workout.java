package inhiit;


import javax.persistence.*;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private int reps;

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getReps() {
        return reps;
    }

    private int intervalone;

    private int intervaltwo;

    private int cycles;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIntervalone(int intervalone) {
        this.intervalone = intervalone;
    }

    public void setIntervaltwo(int intervaltwo) {
        this.intervaltwo = intervaltwo;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public int getIntervalone() {
        return intervalone;
    }

    public int getIntervaltwo() {
        return intervaltwo;
    }

    public int getCycles() {
        return cycles;
    }
}
