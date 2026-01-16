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
        System.out.println("----- SUBMISSION LOGGED -----");
        System.out.println("ID: " + submissionId);
        System.out.println("File: " + filePath);
        System.out.println("Status: " + status);
        System.out.println("Timestamp: " + new java.util.Date());
        System.out.println("-----------------------------");
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getFilePath() {
        return filePath;
    }
}
