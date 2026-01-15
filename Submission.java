public class Submission {
    private String submissionId;
    private String filePath;
    private String status;

    public Submission(String submissionId, String filePath) {
        this.submissionId = submissionId;
        this.filePath = filePath;
        this.status = "Submitted";
    }

    public void recordSubmission() {
    }
}
