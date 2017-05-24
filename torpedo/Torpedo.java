package torpedo;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/**
 * Class is the main frame and contains the main method of the client
 * functionality.
 *
 * @author mmeta
 */
public class Torpedo extends JFrame {

    /**
     * Class constructor, calls the InitUI method.
     */
    public Torpedo() {

        initUI();

    }

    /**
     * Sets the basic settings of the frame.
     */
    private void initUI() {
        add((JPanel) new Board());
        setTitle("Torpedo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    /**
     * Main method, creates the Torpedo object.
     *
     * @param args Expects no command line arguments.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            Torpedo torpedo = new Torpedo();
            torpedo.setVisible(true);
        });
    }

}
