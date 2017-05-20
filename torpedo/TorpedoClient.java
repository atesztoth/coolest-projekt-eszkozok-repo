package torpedo;


import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Class that implements the main functionality of the client.
 *
 * @author mmeta
 */
public class TorpedoClient {

    private final JPanel panel;
    private final TorpedoView view;
    private boolean myTurn;
    private PrintWriter pw;
    private Scanner sc;
    private int[][] map;
    private boolean start = false;
    private boolean game = true;

    /**
     * Class constructor.
     *
     * @param panel The panel on which we draw on.
     * @param view The object which draws our interface.
     */
    public TorpedoClient(JPanel panel, TorpedoView view) {
        this.panel = panel;
        this.view = view;
        panel.repaint();

    }

    /**
     * Starts the client. It connects to the server and answers to the first
     * step if it goes second.
     *
     * @throws IOException If an input or output exception occurred.

     */
    private void start() throws IOException {
        try {
            //System.out.println("Start");
            final String gep = "localhost";
            final int port = 60504;
            boolean game = true;
            boolean win = false;

            Socket s = new Socket(gep, port);
            pw = new PrintWriter(s.getOutputStream(), true);
            sc = new Scanner(s.getInputStream());
            LinkedList<String> ships = new LinkedList<>();
            String valasz;

            view.setWait();
            panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());

            for (int i = 0; i < 5; i++) {
                valasz = sc.nextLine();
                ships.add(valasz);
            }
            valasz = sc.nextLine();
            //System.out.println(valasz);
            myTurn = valasz.equals("FIRST");
            createView(ships, myTurn);

            if (!myTurn) {
                valasz = sc.nextLine();
                String[] coord = valasz.split(" ");
                if (map[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])] == 0) {
                    pw.println("MISS");
                } else {
                    pw.println("HIT");
                    map[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])] = 0;
                }
                view.setTile(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
                myTurn = true;

            } else {
                view.setTurn(true);
            }
            view.setTurn(true);
            panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
        } catch (NoSuchElementException | ConnectException e) {
            view.setError(true);
            panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
            game = false;
        }
    }

    /**
     * It creates the map of the interface, which the ships are on. Converts the
     * shipLines to the representation that the view can use.
     *
     * @param myTurn Whether it's the plyers turn or not.
     * @param shipLines List which contains the ships coordinates in String
     * format, that we received from the server.
     */
    private void createView(LinkedList<String> shipLines, boolean myTurn) {
        int[][] types = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                types[i][j] = 0;
            }
        }
        for (String s : shipLines) {
            String[] shipString = s.split(" ");
            int[] ship = new int[shipString.length];
            for (int j = 0; j < ship.length; j++) {
                ship[j] = Integer.parseInt(shipString[j]);
            }
            for (int i = 0; i < ship.length; i = i + 2) {
                types[ship[i]][ship[i + 1]] = 3;
            }

        }
        map = types;
        view.setTiles(types);
        view.setTurn(myTurn);
        start = true;
        view.setStart(true);
        panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
            }
        });

    }

    /**
     * Send our target to the server and modifies the view and the client based
     * on the answer. It only send the target, if it's valid, so it's an empty
     * tile on the enemy's field.
     *
     * @param e the MouseEvent
     */
    void mousePressed(MouseEvent e) {
        new Thread() {
            @Override
            public void run() {
                try {
                    //System.out.println(" start:"+start+" game:"+game+" myTurn:"+myTurn);
                    if (start && game && myTurn) {

                        String target = view.getTarget(e);
                        if (target == null) {
                            return;
                        }
                        myTurn = false;
                        pw.println(target);
                        String valasz = sc.nextLine();
                        //System.out.println(valasz);
                        if (valasz.equals("VEGE")) {
                            game = false;
                            view.setWin(true);
                            view.setTarget(target, true);
                            panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
                        } else {
                            if (valasz.equals("HIT")) {
                                view.setTarget(target, true);
                            } else {
                                view.setTarget(target, false);
                            }
                            view.setTurn(false);
                            panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
                            valasz = sc.nextLine();
                            String[] coord = valasz.split(" ");
                            if (gameOver(coord)) {
                                game = false;
                                pw.println("VESZTETTEM");
                                view.setLose(true);
                            } else if (map[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])] == 0) {
                                pw.println("MISS");
                            } else {
                                pw.println("HIT");
                                map[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])] = 0;
                            }
                            view.setTile(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
                            view.setTurn(true);
                            panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
                        }
                        myTurn = true;

                    }
                } catch (NoSuchElementException e) {
                    view.setError(true);
                    panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
                    game = false;
                }
            }
        }.start();
    }

    /**
     * Method responsible for calling the mousePressed method of the client.
     *
     * @param e the KeyEvent
     */
    void keyPressed(KeyEvent e) {
        if (start == false) {
            try {
                start();
            } catch (IOException ex) {
                Logger.getLogger(TorpedoClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
    * Checks if all the ships is destroyed.
    *
    * @param coord Matrix which contains the positions of the ships, past tips.
    * @return Whether the game is over.
    */
    private boolean gameOver(String[] coord) {
        if (map[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])] == 3) {
            int count = 0;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (map[i][j] == 3) {
                        count++;
                    }
                }
            }
            if (count == 1) {
                return true;
            }

        }
        return false;
    }

}
