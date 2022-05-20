import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Bomb extends Entity<Bomb>{
    private int COOLDOWN = 30; // number of frames the bomb should be in the exploded state
    private int sinceExplosion = 0; // counts the number of frames the bomb has been in the exploded state
    private boolean exploded = false;
    private Image EXPLOSION_IMAGE = new Image("res/explosion.png");
    private boolean remove = false; // if the bomb is to be removed from the game (stop being rendered)
    public Bomb(int startX, int startY) {
        super(10, startX, startY, "bomb", new Image("res/bomb.png"));
    }


    /**
     * Method that performs state update, returns true if bomb is to be removed from game
     */
    public boolean update(Sailor sailor, int itemsCollected) {
        if (ENTITY_BOX.intersects(sailor.getCharacterBox())) { // if sailor collides with the bomb
            if (!exploded) {
                sailor.setHealthPoints(sailor.getHealthPoints() - DAMAGE_POINTS);
                System.out.println("Bomb inflicts " + DAMAGE_POINTS
                                + " damage points on Sailor. Sailor’s current health: "
                        + sailor.getHealthPoints() + "/" + sailor.getMaxHealthPoints());
            }
            exploded = true;
            sailor.moveBack();
        }

        if (exploded == true) {
            sinceExplosion += 1;
            if (sinceExplosion <= COOLDOWN) {
                EXPLOSION_IMAGE.drawFromTopLeft(X, Y);
            } else {
                remove = true;
            }
        } else {
            ENTITY_IMAGE.drawFromTopLeft(X, Y);
        }
        return remove;
    }
}