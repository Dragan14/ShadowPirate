import bagel.Image;
import bagel.util.Rectangle;

public abstract class Entity<T extends Entity> {
    protected final Image ENTITY_IMAGE;
    protected final int DAMAGE_POINTS;
    private final int X;
    private final int Y;
    private final Rectangle ENTITY_BOX;
    private final String entityType;

    public Entity(int DAMAGE_POINTS, int startX, int startY, String entityType, Image ENTITY_IMAGE) {
        this.DAMAGE_POINTS = DAMAGE_POINTS;
        this.X = startX;
        this.Y = startY;
        this.ENTITY_IMAGE = ENTITY_IMAGE;
        this.ENTITY_BOX = new Rectangle(startX, startY, ENTITY_IMAGE.getWidth(), ENTITY_IMAGE.getHeight());
        this.entityType = entityType;
    }

    /**
     * Method that performs state update, returns true if bomb is to be removed from game
     */
    public abstract boolean update(Sailor sailor);

    public Rectangle getEntityBox() {
        return ENTITY_BOX;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

}
