import javax.swing.*;
import java.awt.*;

public class StudentGUI extends JFrame {

    public StudentGUI() {
        setTitle("Student Module");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton btnRegister = new JButton("Register for Seminar");
        JButton btnUpload = new JButton("Upload Presentation");
        JButton btnViewAward = new JButton("View Award");

        setLayout(new GridLayout(4, 1));
        add(new JLabel("Student Functions", SwingConstants.CENTER));
        add(btnRegister);
        add(btnUpload);
        add(btnViewAward);

        btnRegister.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Registration submitted (demo)"));

        //IMPLEMENTING UPLOAD LOGIC
        btnUpload.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Presentation File");
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                
                // For demo purposes, we create a temporary student instance
                // In a real app, you'd use the logged-in student's object
                Student currentStudent = new Student("S-001", "Varya", "varya@univ.edu", 
                                                    "AI Research", "Abstract...", "Oral");

                boolean success = currentStudent.submitPresentation(filePath);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "File Uploaded Successfully!\nPath: " + filePath);
                }
            }
        });

        btnViewAward.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Award result will be shown by Coordinator"));
    }
}
