public class Student extends User {

    private String researchTitle;
    private String abstractText;
    private String presentationType;

    public Student(String userId, String name, String email,
                   String researchTitle, String abstractText, String presentationType) {
        super(userId, name, email);
        this.researchTitle = researchTitle;
        this.abstractText = abstractText;
        this.presentationType = presentationType;
    }

    public boolean register() {
        return true;
    }

    public boolean submitPresentation() {
        return true;
    }

    public void receiveAward() {
    }

    public String getPresentationType() {
        return presentationType;
    }
}
