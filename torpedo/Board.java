
package torpedo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Board extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    TorpedoView view;
    TorpedoClient client;
    
    public Board() {
        initBoard();

    }

    private void initBoard() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.getWidth();

        //setPreferredSize(new Dimension((int) (screenSize.getWidth() - 300), (int) ((screenSize.getWidth() - 300) / 16 * 9)));
        setPreferredSize(new Dimension(800, 500));
        addKeyListener(new Controller());
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        setFocusable(true);
        setBackground(new Color(1, 95, 135));
        setDoubleBuffered(true);
        paintImmediately(0, 0, getWidth(), getHeight());
        view = new TorpedoView(getPreferredSize().getWidth(),getPreferredSize().getHeight());
        client = new TorpedoClient(this, view);    

    }
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        view.draw(g);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    void update() {
       
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        client.mousePressed(e);
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }

    @Override
    public void mouseDragged(MouseEvent e) {
       
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    private class Controller extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
           
        }

        @Override
        public void keyPressed(KeyEvent e) {
           client.keyPressed(e);
        }
    }

}
