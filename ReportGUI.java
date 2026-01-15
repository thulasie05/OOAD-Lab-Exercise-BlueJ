import javax.swing.*;
import java.awt.*;

public class ReportGUI extends JFrame {

    private JTextArea reportArea;
    private JButton btnGenerate;

    public ReportGUI(Report report) {
        setTitle("Report & Summary");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        reportArea = new JTextArea();
        reportArea.setEditable(false);

        btnGenerate = new JButton("Generate Final Report");

        btnGenerate.addActionListener(e -> {
            reportArea.setText(report.generate());

            reportArea.append("\nAwards:\n");
            for (Award a : report.getAwards()) {
                if (a.getWinner() != null) {
                    reportArea.append(
                        a.getAwardName() + " - "
                        + a.getWinner().getName() + "\n"
                    );
                }
            }
        });

        add(new JScrollPane(reportArea), BorderLayout.CENTER);
        add(btnGenerate, BorderLayout.SOUTH);
    }
}
