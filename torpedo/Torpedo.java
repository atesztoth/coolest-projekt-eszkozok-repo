/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torpedo;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/**
 *
 * @author mmeta
 */
public class Torpedo extends JFrame {

    public Torpedo() {

        initUI();

    }

    private void initUI() {
        add((JPanel)new Board());
        setTitle("Torpedo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

  
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            Torpedo torpedo = new Torpedo();
            torpedo.setVisible(true);
            });
    }
    
}
