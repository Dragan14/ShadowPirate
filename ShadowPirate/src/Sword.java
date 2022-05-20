import bagel.Image;

import java.awt.*;

public class Sword extends Entity<Sword> {
    private final int DAMAGE_INCREASE = 15;
    private boolean collected = false;
    public Image ICON = new Image("res/items/swordIcon.png");

    public Sword(int startX, int startY) {
        super(0, startX, startY, "sword", new Image("res/items/sword.png"));
    }

    /**
     * Method that performs state update if the sword has been collected
     */
    public boolean update(Sailor sailor, int itemsCollected) {

        if (collected == true) { // if the sword has been collected
            ICON.drawFromTopLeft(1, getItemNumber() * ICON_OFFSET + ICON_OFFSET);
            return false;
        }

        // sailor collects the sword
        if (ENTITY_BOX.intersects(sailor.getCharacterBox())) {
            sailor.setDamagePoints(sailor.getdamagePoints() + DAMAGE_INCREASE); // increase damage of the sailor
            //Sailor finds Sword. Sailor’s damage points increased to 30
            System.out.println("Sailor finds Sword. Sailor's damage points increased to " + sailor.getdamagePoints());
            collected = true;
            setItemNumber(itemsCollected);
            return true;
        }

        ENTITY_IMAGE.drawFromTopLeft(X, Y);
        return false;
    }
}
