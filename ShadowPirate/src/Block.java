import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Block extends Entity<Block>{
    public Block(int startX, int startY) {
        super(0, startX, startY, "block", new Image("res/block.png"));
    }

    /**
     * Method that performs state update
     */
    public boolean update(Sailor sailor) {
        ENTITY_IMAGE.drawFromTopLeft(getX(), getY());

        if (getEntityBox().intersects(sailor.getCharacterBox())) {
            sailor.moveBack();
        }

        return false;
    }
}