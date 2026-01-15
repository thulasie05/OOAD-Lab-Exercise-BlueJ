import java.util.List;
import java.util.ArrayList;

public class Award {
    private String awardId;
    private String awardName;
    private String criteria;
    private Student winner;
    private String awardType; // "Best Oral", "Best Poster", "People's Choice"
    private double prizeAmount;
    private boolean isGenerated;
    
    public Award(String awardId, String awardName, String criteria, String awardType) {
        this.awardId = awardId;
        this.awardName = awardName;
        this.criteria = criteria;
        this.awardType = awardType;
        this.prizeAmount = 0.0;
        this.isGenerated = false;
    }
    
    public Award(String awardId, String awardName, String criteria, String awardType, double prizeAmount) {
        this(awardId, awardName, criteria, awardType);
        this.prizeAmount = prizeAmount;
    }
    
    // Calculate winner based on evaluation scores
    public void calculateWinner(List<Evaluation> evaluations) {
        double maxScore = -1;
        Student potentialWinner = null;
        
        for (Evaluation e : evaluations) {
            // Only consider students with matching presentation type for specific awards
            if (awardType.equals("Best Oral") && 
                !e.getStudent().getPresentationType().equalsIgnoreCase("Oral")) {
                continue;
            }
            if (awardType.equals("Best Poster") && 
                !e.getStudent().getPresentationType().equalsIgnoreCase("Poster")) {
                continue;
            }
            
            double totalScore = e.calculateTotalScore();
            if (totalScore > maxScore) {
                maxScore = totalScore;
                potentialWinner = e.getStudent();
            }
        }
        
        this.winner = potentialWinner;
        this.isGenerated = true;
    }
    
    // Calculate People's Choice Award (based on votes/ratings)
    public void calculatePeoplesChoice(List<Evaluation> evaluations) {
        double maxVotes = -1;
        Student potentialWinner = null;
        
        for (Evaluation e : evaluations) {
            // Assuming Evaluation has a getVotes() or getRating() method
            // For demo, we'll use a combination of scores
            double popularityScore = e.calculateTotalScore() * 0.7 + 
                                   (e.getComments() != null ? e.getComments().length() * 0.01 : 0);
            
            if (popularityScore > maxVotes) {
                maxVotes = popularityScore;
                potentialWinner = e.getStudent();
            }
        }
        
        this.winner = potentialWinner;
        this.isGenerated = true;
    }
    
    // Validate if winner meets criteria
    public boolean validateCriteria() {
        if (winner == null) return false;
        
        switch (awardType) {
            case "Best Oral":
                return winner.getPresentationType().equalsIgnoreCase("Oral") &&
                       winner.hasUploadedPresentation();
            case "Best Poster":
                return winner.getPresentationType().equalsIgnoreCase("Poster") &&
                       winner.hasUploadedPresentation();
            case "People's Choice":
                return true; // All students eligible
            default:
                return false;
        }
    }
    
    // Generate award certificate/agenda text
    public String generateAwardAgenda() {
        StringBuilder agenda = new StringBuilder();
        agenda.append("=== AWARD CERTIFICATE ===\n");
        agenda.append("Award ID: ").append(awardId).append("\n");
        agenda.append("Award Name: ").append(awardName).append("\n");
        agenda.append("Type: ").append(awardType).append("\n");
        agenda.append("Criteria: ").append(criteria).append("\n");
        
        if (winner != null) {
            agenda.append("\nAWARDED TO:\n");
            agenda.append("Name: ").append(winner.getName()).append("\n");
            agenda.append("Student ID: ").append(winner.getStudentId()).append("\n");
            agenda.append("Research Topic: ").append(winner.getResearchTopic()).append("\n");
            agenda.append("Presentation Type: ").append(winner.getPresentationType()).append("\n");
        }
        
        if (prizeAmount > 0) {
            agenda.append("\nPrize Amount: RM").append(String.format("%.2f", prizeAmount)).append("\n");
        }
        
        agenda.append("\nGenerated on: ").append(new java.util.Date()).append("\n");
        return agenda.toString();
    }
    
    // Getters and Setters
    public Student getWinner() {
        return winner;
    }
    
    public String getAwardName() {
        return awardName;
    }
    
    public String getAwardType() {
        return awardType;
    }
    
    public String getCriteria() {
        return criteria;
    }
    
    public double getPrizeAmount() {
        return prizeAmount;
    }
    
    public void setPrizeAmount(double prizeAmount) {
        this.prizeAmount = prizeAmount;
    }
    
    public boolean isGenerated() {
        return isGenerated;
    }
    
    public String getAwardId() {
        return awardId;
    }
}
