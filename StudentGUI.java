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

        btnUpload.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Presentation uploaded (demo)"));

        btnViewAward.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Award result will be shown by Coordinator"));
    }
}
