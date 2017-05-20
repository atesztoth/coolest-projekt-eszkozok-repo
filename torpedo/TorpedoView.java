
package torpedo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * Class that draws the user interface
 *
 * @author mmeta
 */
class TorpedoView {

    private double width;
    private double height;
    private boolean myTurn;
    private boolean win;
    private boolean lose = false;
    private boolean start = false;
    private boolean error = false;

    /**
     *
     * @param win Boolean to set.
     */
    public void setWin(boolean win) {
        this.win = win;
    }

    /**
     *
     * @param lose Boolean to set.
     */
    public void setLose(boolean lose) {
        this.lose = lose;
    }
    private boolean wait = false;

    /**
     *
     * @param start Boolean to set.
     */
    public void setStart(boolean start) {
        this.start = start;
    }

    private Tile[][] own;
    private Tile[][] enemy;

    /**
     * Class constructor.
     *
     * @param width Width of the panel.
     * @param height Height of the Panel.
     */
    TorpedoView(double width, double height) {
        this.width = width;
        this.height = height;

    }

    /**
     * Draws the interface.
     *
     * @param g Graphics object.
     */
    public void draw(Graphics g) {
        if (!start) {
            if (error) {
                drawError(g);
            } else {
                drawKey(g);
            }

        } else {
            drawText(g);
            drawTiles(g, own);
            drawTiles(g, enemy);
            if (error) {
                drawError(g);
            } else if (win) {
                drawWin(g);
            } else if (lose) {
                drawLose(g);
            } else {
                drawTurn(g, myTurn);
            }

        }

    }

    /**
     * Draws the map..
     *
     * @param g Graphics object.
     * @param tiles Tiles of the map.
     */
    protected void drawTiles(Graphics g, Tile[][] tiles) {

        for (Tile[] tile1 : tiles) {
            for (Tile tile : tile1) {
                g.drawImage(tile.getImage(), (int) tile.getX(),
                        (int) tile.getY(), (int) (tile.getWidth() + 1), (int) tile.getHeight(), null);
            }
        }
    }

    /**
     * Creates the map of tiles at the right place.
     *
     * @param types Types of the tiles.
     */
    public void setTiles(int[][] types) {
        int[][] tmp = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tmp[i][j] = 0;
            }
        }
        enemy = createTiles(tmp, (int) (width / 2 + width / 20), (int) (height / 5));
        own = createTiles(types, (int) (width / 20), (int) (height / 5));
    }

    /**
     * Creates the tiles of the maps.
     *
     * @param types Types of the tiles to create.
     * @param x X coordinate of the map of the tiles.
     * @param y Y coordinate of the map of the tiles.
     */
    private Tile[][] createTiles(int[][] types, int x, int y) {
        Tile[][] tiles = new Tile[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tiles[i][j] = new Tile((int) (x + i * width / 25), (int) (y + j * width / 25), (int) (width / 25), (int) (width / 25), types[i][j]);

            }
        }
        return tiles;
    }

    /**
     * Draws the map text.
     *
     * @param g Graphics object.
     */
    private void drawText(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 15)));
        g.drawString("Own", (int) (width / 5), (int) (height / 7));
        g.drawString("Enemy's", (int) (width / 2 + width / 5), (int) (height / 7));
    }

    /**
     *
     * @param myTurb Boolean to set.
     */
    void setTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    /**
     * Draws the turn text.
     *
     * @param g Graphics object.
     * @param myTurn Whether it's the player turn.
     */
    private void drawTurn(Graphics g, boolean myTurn) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 15)));
        if (myTurn) {
            g.drawString("My turn", (int) (width / 2 - width / 10), (int) (height - height / 10));
        } else {
            g.drawString("Enemy's turn", (int) (width / 2 - width / 10), (int) (height - height / 10));
        }

    }

    /**
     * Get the coordinate of the clicked tile.
     *
     * @param e MouseEvent object.
     * @return Coordinates of the tile clicked. Null if it's outside enemy map.
     */
    String getTarget(MouseEvent e) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (enemy[i][j].contains(e.getPoint()) && enemy[i][j].getType() == 0) {
                    return i + " " + j;
                }
            }
        }
        return null;
    }

    /**
     * Sets the target tile to the correct type.
     *
     * @param target Coordinates of the target.
     * @param hit Target or miss.
     * @return Coordinates of the tile clicked. Null if it's outside enemy map.
     */
    void setTarget(String target, boolean hit) {
        String[] coord = target.split(" ");
        if (hit) {
            enemy[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].setType(1);
        } else {
            enemy[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].setType(2);
        }
    }

    /**
     * Sets own tile based on enemy's tip.
     *
     * @param i X coordinate of enemys tip.
     * @param j Y coordinate of enemys tip.
     * @return Coordinates of the tile clicked. Null if it's outside enemy map.
     */
    void setTile(int i, int j) {
        if (own[i][j].getType() == 3) {
            own[i][j].setType(4);
        } else {
            own[i][j].setType(2);
        }
    }

    /**
     * Draws the win text.
     *
     * @param g Graphics object.
     */
    private void drawWin(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 15)));
        g.drawString("You WIN", (int) (width / 2 - width / 10), (int) (height - height / 10));
    }

    /**
     * Draws the lose text.
     *
     * @param g Graphics object.
     */
    private void drawLose(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 15)));
        g.drawString("You LOSE", (int) (width / 2 - width / 10), (int) (height - height / 10));
    }

    /**
     * Draws the waiting text.
     *
     * @param g Graphics object.
     */
    private void drawKey(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 10)));
        if (!wait) {
            g.drawString("Press any key to start...", (int) (width / 5), (int) (height / 2 - height / 10));
        } else {
            g.drawString("Waiting for other player", (int) (width / 5), (int) (height / 2 - height / 10));
        }

    }

    /**
     *
     * Set wait to true.
     */
    void setWait() {
        wait = true;
    }

    /**
     *
     * @param error Boolean to set.
     */
    void setError(boolean error) {
        this.error = error;
    }

    /**
     * Draws the error text.
     *
     * @param g Graphics object.
     */
    private void drawError(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 15)));
        g.drawString("Kapcsolat megszakadt", (int) (width / 2 - width / 8), (int) (height - height / 10));
    }
}
