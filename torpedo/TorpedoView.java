/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torpedo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author mmeta
 */
class TorpedoView {

    private double width;
    private double height;
    private boolean myTurn;
    private boolean win;
    private boolean lose=false;
    private boolean start = false;
    private boolean error = false;
    
    public void setWin(boolean win) {
        this.win = win;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }
    private boolean wait= false;

    public void setStart(boolean start) {
        this.start = start;
    }

    private Tile[][] own;
    private Tile[][] enemy;

    TorpedoView(double width, double height) {
        this.width = width;
        this.height = height;

    }

    public void draw(Graphics g) {
        if (!start) {
            drawKey(g);
        } else {
            drawText(g);
            drawTiles(g, own);
            drawTiles(g, enemy);
            if(error){
                drawError(g);
            }
            else if (win) {
                drawWin(g);
            }
            else if (lose) {
                drawLose(g);
            }else{
                drawTurn(g, myTurn);
            }

        }

    }

    protected void drawTiles(Graphics g, Tile[][] tiles) {

        for (Tile[] tile1 : tiles) {
            for (Tile tile : tile1) {
                g.drawImage(tile.getImage(), (int) tile.getX(),
                        (int) tile.getY(), (int) (tile.getWidth() + 1), (int) tile.getHeight(), null);
            }
        }
    }

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

    private Tile[][] createTiles(int[][] types, int x, int y) {
        Tile[][] tiles = new Tile[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tiles[i][j] = new Tile((int) (x + i * width / 25), (int) (y + j * width / 25), (int) (width / 25), (int) (width / 25), types[i][j]);

            }
        }
        return tiles;
    }

    private void drawText(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 15)));
        g.drawString("Own", (int) (width / 5), (int) (height / 7));
        g.drawString("Enemy's", (int) (width / 2 + width / 5), (int) (height / 7));
    }

    void setTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    private void drawTurn(Graphics g, boolean myTurn) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 15)));
        if (myTurn) {
            g.drawString("My turn", (int) (width / 2 - width / 10), (int) (height - height / 10));
        } else {
            g.drawString("Enemy's turn", (int) (width / 2 - width / 10), (int) (height - height / 10));
        }

    }

    String getTarget(MouseEvent e) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (enemy[i][j].contains(e.getPoint()) && enemy[i][j].getType()==0) {
                    return i + " " + j;
                }
            }
        }
        return null;
    }

    void setTarget(String target, boolean b) {
        String[] coord = target.split(" ");
        if (b) {
            enemy[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].setType(1);
        } else {
            enemy[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].setType(2);
        }
    }

    void setTile(int i, int j) {
        if (own[i][j].getType() == 3) {
            own[i][j].setType(4);
        } else {
            own[i][j].setType(2);
        }
    }

    private void drawWin(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 15)));
        g.drawString("You WIN", (int) (width / 2 - width / 10), (int) (height - height / 10));
    }

    private void drawLose(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 15)));
        g.drawString("You LOSE", (int) (width / 2 - width / 10), (int) (height - height / 10));
    }

    private void drawKey(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 10)));
        if(!wait){
          g.drawString("Press any key to start...", (int) (width / 5), (int) (height/2 - height / 10));  
        }else{
            g.drawString("Waiting for other player", (int) (width / 5), (int) (height/2 - height / 10));
        }
        
    }

    void setWait() {
        wait=true;
    }

    void setError(boolean b) {
        error=b;
    }

    private void drawError(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int) (height / 15)));
        g.drawString("Kapcsolat megszakadt", (int) (width / 2 - width / 8), (int) (height - height / 10));
    }
}
