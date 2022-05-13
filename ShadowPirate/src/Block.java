import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Block{
    private final static Image BLOCK = new Image("res/block.png");
    private final static int DAMAGE_POINTS = 10;
    private final int x;
    private final int y;
    private Rectangle blockBox;

    public Block(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.blockBox = new Rectangle(startX, startY, BLOCK.getWidth(), BLOCK.getHeight());
    }

    /**
     * Method that performs state update
     */
    public void update() {
        BLOCK.drawFromTopLeft(x, y);
    }

    public int getDAMAGE_POINTS() {
        return DAMAGE_POINTS;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBlockBox() {
        return blockBox;
    }
}