import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AwardGUI extends JFrame {

    private JTextArea output;
    private JButton btnGenerate;

    public AwardGUI(List<Award> awards, List<Evaluation> evaluations) {
        setTitle("Award Module");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        output = new JTextArea();
        output.setEditable(false);

        btnGenerate = new JButton("Generate Award Winners");

        btnGenerate.addActionListener(e -> {
            output.setText("");
            for (Award a : awards) {
                a.calculateWinner(evaluations);
                if (a.getWinner() != null) {
                    output.append(
                        a.getAwardName() + "\nWinner: "
                        + a.getWinner().getName() + "\n\n"
                    );
                }
            }
        });

        add(new JScrollPane(output), BorderLayout.CENTER);
        add(btnGenerate, BorderLayout.SOUTH);
    }
}
