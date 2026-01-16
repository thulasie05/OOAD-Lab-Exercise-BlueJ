import javax.swing.*;
import java.awt.*;

public class EvaluatorGUI extends JFrame {

    public EvaluatorGUI() {
        setTitle("Evaluator Module");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton btnReview = new JButton("Review Presentation");
        JButton btnEvaluate = new JButton("Evaluate Student");

        setLayout(new GridLayout(3, 1));
        add(new JLabel("Evaluator Functions", SwingConstants.CENTER));
        add(btnReview);
        add(btnEvaluate);

        btnReview.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Presentation reviewed (demo)"));

        btnEvaluate.addActionListener(e -> {
            String sName = JOptionPane.showInputDialog(this, "Enter Student Name:");
            if (sName == null || sName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: Student name is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            //Dropdown for Rubric (Eliminates typing errors)
            String[] rubricOptions = {"Oral", "Poster"};
            String selectedRubric = (String) JOptionPane.showInputDialog(
                    this, 
                    "Select Rubric Type:", 
                    "Rubric Selection", 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    rubricOptions, 
                    rubricOptions[0]
            );
        
            if (selectedRubric == null) return; // User pressed cancel
        
            String scoreStr = JOptionPane.showInputDialog(this, "Enter Score (0-100):");
            if (scoreStr == null) return;
        
            try {
                int sScore = Integer.parseInt(scoreStr);
                if (sScore < 0 || sScore > 100) {
                    JOptionPane.showMessageDialog(this, "Error: Score must be between 0-100.");
                    return;
                }
        
                String sComment = JOptionPane.showInputDialog(this, "Enter Comments:");
                if (sComment == null) sComment = "";
        
                //Create Student and Evaluation
                //We use the 'selectedRubric' directly which is already properly capitalized
                Student targetStudent = new Student("STU-" + (int)(Math.random()*100), 
                                                    sName, sName.toLowerCase() + "@univ.edu", 
                                                    "Research Title", "Abstract Text", selectedRubric);
        
                Evaluation eval = new Evaluation("EVAL-" + System.currentTimeMillis(), 
                                                 selectedRubric, sScore, sComment, targetStudent);
                
                // 6. Record the evaluation
                eval.recordEvaluation(); 
                JOptionPane.showMessageDialog(this, "Evaluation successfully recorded for " + sName);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Score must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
