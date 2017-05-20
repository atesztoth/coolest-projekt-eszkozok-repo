package torpedo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mmeta
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class TorpedoServer {

    private final int port;
    private ServerSocket server;
    private int id = 1;
    private String fileInput;
    private String fileOutput;
    private int player = 2;

    TorpedoServer(int port) throws IOException {
        this.port = port;
        server = new ServerSocket(port);
    }

    //fogadja a klienseket es minden jatekmenethez letrehoz egy uj szalat
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

    public static void main(String[] args) {
        try {
            TorpedoServer server = new TorpedoServer(60504);
            server.acceptClients();
        } catch (IOException e) {
            System.err.println("Hiba a szerver inditasanal.");
            e.printStackTrace();
        }
    }

    class Game {

        private int id;
        private PrintWriter[] pw;
        private Scanner[] sc;
        private LinkedList<String>[] ships;
        private String[] names;

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
                        //System.out.println("fuck");
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
