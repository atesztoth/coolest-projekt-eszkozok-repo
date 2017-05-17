
package torpedo;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


public class Tile extends Rectangle {

    private int type;
    private Image image;

    public Tile(int x, int y, int width, int height, int type) {
        super(x, y, width, height);
        this.type = type;
        loadImage(type);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        loadImage(type);
    }

    public Image getImage() {
        return image;
    }

    private void loadImage(int type) {
        switch (type) {

            case 0:
                this.image = new ImageIcon(("images/tiles/base.png")).getImage();
                break;
            case 1:
                this.image = new ImageIcon(("images/tiles/hit.png")).getImage();
                break;
            case 2:
                this.image = new ImageIcon(("images/tiles/miss.png")).getImage();
                break;
            case 3:
                this.image = new ImageIcon(("images/tiles/ship.png")).getImage();
                break;
            case 4:
                this.image = new ImageIcon(("images/tiles/damagedship.png")).getImage();
                break;    
            
        }
    }



}
