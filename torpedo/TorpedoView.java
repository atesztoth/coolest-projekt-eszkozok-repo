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

    private Tile[][] own;
    private Tile[][] enemy;

    TorpedoView(double width, double height) {
        this.width = width;
        this.height = height;
        int[][] tmp = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tmp[i][j] = 0;
            }
        }
        tmp[3][5] = 1;
        tmp[7][8] = 2;
        enemy=createTiles(tmp,(int)(width/2+width/20),(int)(height/5));
        tmp[3][5] = 0;
        tmp[7][8] = 0;
        tmp[1][1] = 3;
        tmp[1][2] = 3;
        tmp[1][4] = 3;
        tmp[1][5] = 4;

        own = createTiles(tmp,(int)(width/20),(int)(height/5));
        
    }

    void draw(Graphics g) {
        drawText(g);
        drawTiles(g, own);
        drawTiles(g, enemy);

    }

    protected void drawTiles(Graphics g, Tile[][] tiles) {

        for (Tile[] tile1 : tiles) {
            for (Tile tile : tile1) {
                g.drawImage(tile.getImage(), (int) tile.getX(),
                        (int) tile.getY(), (int) (tile.getWidth() + 1), (int) tile.getHeight(), null);
            }
        }
    }

    private Tile[][] createTiles(int[][] types,int x, int y) {
        Tile[][] tiles = new Tile[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tiles[i][j] = new Tile((int)(x + i *width/25) , (int)(y+ j *width/25 ) , (int)(width/25),(int)(width/25) , types[i][j]);
                        
            }
        }
        return tiles;
    }

    private void drawText(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int)(height / 15)));
        g.drawString("Sajat", (int)(width/5), (int)(height/7));
        g.drawString("Ellenfel", (int)(width/2+width/5), (int)(height/7));
    }

}
