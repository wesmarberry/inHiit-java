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

    private int intervalOne;

    private int intervalTwo;

    private int cycles;

    public void setId(Long id) {
        this.id = id;
    }

    public void setIntervalTwo(int intervalTwo) {
        this.intervalTwo = intervalTwo;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public Long getId() {
        return id;
    }

    public int getIntervalTwo() {
        return intervalTwo;
    }

    public int getCycles() {
        return cycles;
    }

    public void setIntervalOne(int intervalOne) {
        this.intervalOne = intervalOne;
    }

    public int getIntervalOne() {
        return intervalOne;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
