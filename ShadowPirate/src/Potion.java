import bagel.Image;

import java.awt.*;

public class Potion extends Entity<Potion> {
    private final int HEALTH_INCREASE = 15;
    private boolean collected = false;
    public Image ICON = new Image("res/items/potionIcon.png");

    public Potion(int startX, int startY) {
        super(0, startX, startY, "potion", new Image("res/items/potion.png"));
    }

    /**
     * Method that performs state update if the potion has been collected
     */
    public boolean update(Sailor sailor, int itemsCollected) {

        // if the potion has been collected
        if (collected == true) {
            ICON.drawFromTopLeft(1, getItemNumber() * ICON_OFFSET + ICON_OFFSET);
            return false;
        }

        // the sailor collects the potion
        if (ENTITY_BOX.intersects(sailor.getCharacterBox())) {
            // potion cannot increase health more than the maximum health
            if ((sailor.getHealthPoints() + HEALTH_INCREASE) > sailor.getMaxHealthPoints()) {
                sailor.setHealthPoints(sailor.getMaxHealthPoints());
            } else {
                sailor.setHealthPoints(sailor.getHealthPoints() + HEALTH_INCREASE);
            }
            //Sailor finds Potion. Sailor’s current health: 95/100
            System.out.println("Sailor finds Potion. Sailor's current health: " + sailor.getHealthPoints() + "/" + sailor.getMaxHealthPoints());
            collected = true;
            setItemNumber(itemsCollected);
            return true;
        }

        ENTITY_IMAGE.drawFromTopLeft(X, Y);
        return false;
    }
}