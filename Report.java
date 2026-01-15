import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class Report {
    private String reportId;
    private String reportType;
    private Date generateDate;
    private List<Evaluation> evaluations;
    private List<Award> awards;
    
    // Report types as per requirements
    public static final String EVALUATION_SUMMARY = "Evaluation Summary Report";
    public static final String AWARD_SUMMARY = "Award Summary Report";
    public static final String SEMINAR_FINAL = "Final Seminar Report";
    public static final String SCHEDULE_REPORT = "Seminar Schedule";
    public static final String PARTICIPANT_LIST = "Participant List";
    
    public Report(String reportId, String reportType,
                  List<Evaluation> evaluations, List<Award> awards) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.evaluations = evaluations != null ? new ArrayList<>(evaluations) : new ArrayList<>();
        this.awards = awards != null ? new ArrayList<>(awards) : new ArrayList<>();
        this.generateDate = new Date();
    }
    
    // Generate comprehensive report based on type
    public String generate() {
        StringBuilder result = new StringBuilder();
        
        // Header with timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        result.append("=== ").append(reportType.toUpperCase()).append(" ===\n");
        result.append("Generated: ").append(sdf.format(generateDate)).append("\n");
        result.append("Report ID: ").append(reportId).append("\n");
        result.append("=========================================\n\n");
        
        // Generate different sections based on report type
        switch(reportType) {
            case EVALUATION_SUMMARY:
                result.append(generateEvaluationSection());
                break;
            case AWARD_SUMMARY:
                result.append(generateAwardSection());
                break;
            case SEMINAR_FINAL:
                result.append(generateFinalReport());
                break;
            case SCHEDULE_REPORT:
                result.append(generateScheduleSection());
                break;
            case PARTICIPANT_LIST:
                result.append(generateParticipantSection());
                break;
            default:
                result.append(generateEvaluationSection());
                result.append("\n");
                result.append(generateAwardSection());
        }
        
        // Add analytics/statistics
        result.append(generateStatistics());
        
        return result.toString();
    }
    
    private String generateEvaluationSection() {
        StringBuilder section = new StringBuilder();
        section.append("--- EVALUATION RESULTS ---\n");
        
        if (evaluations.isEmpty()) {
            section.append("No evaluations available.\n\n");
            return section.toString();
        }
        
        // Table header
        section.append(String.format("%-25s %-15s %-15s %-10s %-30s\n", 
            "Student", "Presentation", "Rubric", "Score", "Comments"));
        section.append("------------------------------------------------------------------------------------------------\n");
        
        for (Evaluation e : evaluations) {
            Student s = e.getStudent();
            String comments = e.getComments();
            if (comments.length() > 25) {
                comments = comments.substring(0, 22) + "...";
            }
            
            section.append(String.format("%-25s %-15s %-15s %-10.1f %-30s\n",
                s.getName(),
                s.getPresentationType(),
                e.getRubricType(),
                e.calculateTotalScore(),
                comments));
        }
        section.append("\n");
        
        return section.toString();
    }
    
    private String generateAwardSection() {
        StringBuilder section = new StringBuilder();
        section.append("--- AWARD WINNERS ---\n");
        
        if (awards.isEmpty()) {
            section.append("No awards available.\n\n");
            return section.toString();
        }
        
        int awardCount = 0;
        for (Award a : awards) {
            awardCount++;
            section.append(awardCount).append(". ").append(a.getAwardName()).append("\n");
            section.append("   Criteria: ").append(a.getCriteria()).append("\n");
            
            if (a.getWinner() != null) {
                section.append("   🏆 Winner: ").append(a.getWinner().getName()).append("\n");
                
                // Find winner's score if available
                for (Evaluation e : evaluations) {
                    if (e.getStudent().equals(a.getWinner())) {
                        section.append("   Score: ").append(e.calculateTotalScore()).append("\n");
                        break;
                    }
                }
            } else {
                section.append("   Status: Not yet assigned\n");
            }
            
            if (a.getAwardName().equals(Award.PEOPLES_CHOICE)) {
                section.append("   Total Votes: ").append(a.getVoteCount()).append("\n");
                section.append("   Nominees: ");
                for (Student nominee : a.getNominees()) {
                    section.append(nominee.getName()).append(", ");
                }
                if (!a.getNominees().isEmpty()) {
                    section.delete(section.length()-2, section.length()); // Remove last comma
                }
                section.append("\n");
            }
            section.append("\n");
        }
        
        return section.toString();
    }
    
    private String generateFinalReport() {
        StringBuilder section = new StringBuilder();
        section.append("--- FINAL SEMINAR REPORT ---\n\n");
        section.append(generateEvaluationSection());
        section.append(generateAwardSection());
        section.append("--- SEMINAR OVERVIEW ---\n");
        section.append("This report summarizes the Postgraduate Academic Research Seminar.\n");
        section.append("The seminar included presentations from Master's and PhD students,\n");
        section.append("with evaluations conducted by faculty panel members.\n\n");
        
        return section.toString();
    }
    
    private String generateScheduleSection() {
        StringBuilder section = new StringBuilder();
        section.append("--- SEMINAR SCHEDULE ---\n");
        section.append("Date: To be announced\n");
        section.append("Venue: FCI Seminar Hall\n");
        section.append("Session Types: Oral Presentations, Poster Sessions\n");
        section.append("Time: 9:00 AM - 5:00 PM\n\n");
        return section.toString();
    }
    
    private String generateParticipantSection() {
        StringBuilder section = new StringBuilder();
        section.append("--- PARTICIPANT LIST ---\n");
        
        if (evaluations.isEmpty()) {
            section.append("No participants.\n");
            return section.toString();
        }
        
        int oralCount = 0, posterCount = 0;
        for (Evaluation e : evaluations) {
            Student s = e.getStudent();
            section.append("• ").append(s.getName());
            section.append(" (").append(s.getPresentationType()).append(")\n");
            
            if ("Oral".equalsIgnoreCase(s.getPresentationType())) {
                oralCount++;
            } else if ("Poster".equalsIgnoreCase(s.getPresentationType())) {
                posterCount++;
            }
        }
        
        section.append("\nTotal Participants: ").append(evaluations.size()).append("\n");
        section.append("Oral Presentations: ").append(oralCount).append("\n");
        section.append("Poster Presentations: ").append(posterCount).append("\n");
        
        return section.toString();
    }
    
    private String generateStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("--- STATISTICS ---\n");
        
        if (!evaluations.isEmpty()) {
            double totalScore = 0;
            double maxScore = -1;
            double minScore = 101;
            Student topStudent = null;
            
            for (Evaluation e : evaluations) {
                double score = e.calculateTotalScore();
                totalScore += score;
                
                if (score > maxScore) {
                    maxScore = score;
                    topStudent = e.getStudent();
                }
                if (score < minScore) {
                    minScore = score;
                }
            }
            
            double averageScore = totalScore / evaluations.size();
            int awardsAssigned = 0;
            for (Award a : awards) {
                if (a.isAwardAssigned()) awardsAssigned++;
            }
            
            stats.append("Total Evaluations: ").append(evaluations.size()).append("\n");
            stats.append(String.format("Average Score: %.2f/100\n", averageScore));
            stats.append(String.format("Highest Score: %.1f (%s)\n", maxScore, 
                topStudent != null ? topStudent.getName() : "N/A"));
            stats.append(String.format("Lowest Score: %.1f\n", minScore));
            stats.append("Total Awards: ").append(awards.size()).append("\n");
            stats.append("Awards Assigned: ").append(awardsAssigned).append("\n");
            
            // Calculate distribution by presentation type
            int oralCount = 0, posterCount = 0;
            for (Evaluation e : evaluations) {
                if ("Oral".equalsIgnoreCase(e.getStudent().getPresentationType())) {
                    oralCount++;
                } else {
                    posterCount++;
                }
            }
            stats.append("Oral Presentations: ").append(oralCount).append("\n");
            stats.append("Poster Presentations: ").append(posterCount).append("\n");
        } else {
            stats.append("No evaluation data available.\n");
        }
        
        return stats.toString();
    }
    
    // Generate CSV format for export (simple version)
    public String generateCSV() {
        StringBuilder csv = new StringBuilder();
        csv.append("Student,PresentationType,Score,Award\n");
        
        for (Evaluation e : evaluations) {
            Student s = e.getStudent();
            String awardInfo = "None";
            
            // Check if student won any award
            for (Award a : awards) {
                if (a.getWinner() != null && a.getWinner().equals(s)) {
                    awardInfo = a.getAwardName();
                    break;
                }
            }
            
            csv.append(s.getName()).append(",")
               .append(s.getPresentationType()).append(",")
               .append(e.calculateTotalScore()).append(",")
               .append(awardInfo).append("\n");
        }
        
        return csv.toString();
    }
    
    // Simple export methods (stubs for requirements)
    public void exportPDF() {
        System.out.println("Exporting report as PDF: " + reportType);
        // In a real system, this would use a PDF library
    }
    
    public void exportCSV() {
        System.out.println("Exporting report as CSV: " + reportType);
        System.out.println(generateCSV());
    }
    
    // Getters
    public List<Award> getAwards() {
        return new ArrayList<>(awards);
    }
    
    public List<Evaluation> getEvaluations() {
        return new ArrayList<>(evaluations);
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public Date getGenerateDate() {
        return generateDate;
    }
    
    @Override
    public String toString() {
        return reportType + " (ID: " + reportId + ", Generated: " 
                + new SimpleDateFormat("dd/MM/yyyy").format(generateDate) + ")";
    }
}
