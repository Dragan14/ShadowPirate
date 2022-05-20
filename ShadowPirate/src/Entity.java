import bagel.Image;
import bagel.util.Rectangle;

public abstract class Entity<T extends Entity> {
    protected final Image ENTITY_IMAGE;
    protected final int DAMAGE_POINTS;
    protected final int X;
    protected final int Y;
    protected final Rectangle ENTITY_BOX;
    protected final String ENTITY_TYPE;
    private int itemNumber;
    protected final int ICON_OFFSET = 40;

    public Entity(int DAMAGE_POINTS, int startX, int startY, String ENTITY_TYPE, Image ENTITY_IMAGE) {
        this.DAMAGE_POINTS = DAMAGE_POINTS;
        this.X = startX;
        this.Y = startY;
        this.ENTITY_IMAGE = ENTITY_IMAGE;
        this.ENTITY_BOX = new Rectangle(startX, startY, ENTITY_IMAGE.getWidth(), ENTITY_IMAGE.getHeight());
        this.ENTITY_TYPE = ENTITY_TYPE;
    }

    /**
     * Method that performs state update, returns true if bomb is to be removed from game, or if the entity has been
     * collected
     */
    public abstract boolean update(Sailor sailor, int itemsCollected);

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }
}
