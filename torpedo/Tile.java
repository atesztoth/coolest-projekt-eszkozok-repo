package torpedo;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Class that implements the tiles that creates the map on the the interface.
 *
 * @author mmeta
 */
public class Tile extends Rectangle {

    private int type;
    private Image image;

    /**
     * Class constructor
     *
     * @param x The x coordinate of the upper left corner of the tile.
     * @param y The y coordinate of the upper left corner of the tile.
     * @param width The width of the tile.
     * @param height The height of the Tile
     * @param type The type of the tile, that determines it's image.
     */
    public Tile(int x, int y, int width, int height, int type) {
        super(x, y, width, height);
        this.type = type;
        loadImage(type);
    }

    /**
     * @return Type of the tile.
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the type, and loads the corresponding image.
     *
     * @param type Type to set.
     */
    public void setType(int type) {
        this.type = type;
        loadImage(type);
    }

    /**
     * @return Current loaded image.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Load the corresponding image to the type of the tile.
     *
     * @param type Type of the tile.
     */
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
