package torpedo;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Class that implements the functionality of the server.
 *
 * @author mmeta
 */
public class TorpedoServer {

    /**
     * Port we start the server on.
     */
    private final int port;
    /**
     * ServerSocket object.
     */
    private ServerSocket server;
    /**
     * Id of the game.
     */
    private int id = 1;
    /**
     * Number of players.
     */
    private int player = 2;

    /**
     * Class constructor.
     *
     * @param port Port to start the server on.
     * @throws IOException If an input or output exception occurred.
     */
    TorpedoServer(int port) throws IOException {
        this.port = port;
        server = new ServerSocket(port);
    }

    /**
     *
     * Accepts the client and starts the game.
     */
    public void acceptClients() {
        try {
            Socket[] sockets = new Socket[player];
            for (int i = 0; i < player; i++) {
                sockets[i] = server.accept();
            }
            Game game = new Game(sockets, id++);
            game.run();
        } catch (IOException e) {
            System.err.println("Hiba a kliensek fogadasakor.");
            e.printStackTrace();
        }
    }

    /**
     * Main method, creates the server, and starts to accept clients.
     *
     * @param args Doesn't use the command line parameters.
     */
    public static void main(String[] args) {
        try {
            TorpedoServer server = new TorpedoServer(60504);
            server.acceptClients();
        } catch (IOException e) {
            System.err.println("Hiba a szerver inditasanal.");
            e.printStackTrace();
        }
    }

    /**
     * Class that implements the game logic.
     */
    public class Game {

        /**
         * Id of the game.
         */
        private int id;
        /**
         * PrintWriter object.
         */
        private PrintWriter[] pw;
        /**
         * Scanner object.
         */
        private Scanner[] sc;
        /**
         * Contains the coordinates of the ships in String representation.
         */
        private LinkedList<String>[] ships;

        /**
         * Class constructor.
         *
         * @param sockets Array of the player sockets.
         * @param id Id of the game.
         * @throws IOException If an input or output exception occurred.
         */
        public Game(Socket[] sockets, int id) throws IOException {
            this.id = id;
            pw = new PrintWriter[player];
            sc = new Scanner[player];
            ships = new LinkedList[player];
            for (int i = 0; i < player; i++) {
                pw[i] = new PrintWriter(sockets[i].getOutputStream(), true);
                sc[i] = new Scanner(sockets[i].getInputStream());
                ships[i] = createRandomShips();

            }
        }

        /**
         * Send the messages to the clients, managing the game.
         *
         * @throws IOException If an input or output exception occurred.
         */
        public void run() throws IOException {
            try {
                for (int i = 0; i < player; i++) {
                    for (int j = 0; j < 5; j++) {

                        pw[i].println(ships[i].get(j));
                    }

                }
                pw[0].println("FIRST");
                pw[1].println("SECOND");
                boolean game = true;
                String valasz = "";
                boolean win = false;
                while (game) {
                    for (int i = 0; i < player; i++) {
                        valasz = sc[i].nextLine();
                        pw[(i + 1) % player].println(valasz);
                        valasz = sc[(i + 1) % player].nextLine();
                        if (valasz.equals("VESZTETTEM")) {
                            pw[i].println("VEGE");
                            game = false;
                            win = true;
                            break;
                        } else {
                            pw[i].println(valasz);
                        }
                    }

                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            } finally {
                for (int i = 0; i < player; i++) {
                    pw[i].close();
                }
                server.close();
            }
        }

        /**
         * Creates a random list of ships.
         *
         * @return List of ship coordinates in String format.
         */
        private LinkedList<String> createRandomShips() {
            LinkedList<String> ships = new LinkedList<>();
            Random rand = new Random();
            LinkedList<Integer> shipLines = new LinkedList<>();
            while (shipLines.size() != 5) {
                int line = (int) (rand.nextDouble() * 10);
                if (!shipLines.contains((Integer) line)) {
                    shipLines.add(line);
                }
            }
            int direction = 0;
            if (rand.nextDouble() * 10 > 5) {
                direction = 1;
            }
            int[] shipLengths = new int[]{2, 3, 3, 4, 5};
            String ship;
            for (int i = 0; i < shipLengths.length; i++) {
                ship = "";
                int begin = (int) (rand.nextDouble() * (10 - shipLengths[i]));
                for (int j = begin; j < begin + shipLengths[i]; j++) {
                    if (direction == 0) {
                        ship += shipLines.get(i) + " " + j + " ";
                    } else {
                        ship += j + " " + shipLines.get(i) + " ";
                    }
                }
                ships.add(ship.trim());
            }
            return ships;
        }
    }
}
