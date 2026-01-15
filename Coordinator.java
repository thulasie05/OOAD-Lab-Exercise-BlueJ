import java.util.ArrayList;
import java.util.List;

public class Coordinator extends User {

    private List<Session> managedSessions;

    public Coordinator(String userId, String name, String email) {
        super(userId, name, email);
        managedSessions = new ArrayList<>();
    }

    public void createSession(Session s) {
        managedSessions.add(s);
    }

    public void assignUsers() {
    }

    public Report generateReport(List<Evaluation> evaluations, List<Award> awards) {
        return new Report("R001", "Final Seminar Report", evaluations, awards);
    }

    public void assignAward() {
    }

    public void generateSchedule() {
    }
}
