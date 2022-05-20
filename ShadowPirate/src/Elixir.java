import bagel.Image;

import java.awt.*;

public class Elixir extends Entity<Elixir> {
    private final int MAX_HEALTH_INCREASE = 15;
    private boolean collected = false;
    public final Image ICON = new Image("res/items/elixirIcon.png");

    public Elixir(int startX, int startY) {
        super(0, startX, startY, "elixir", new Image("res/items/elixir.png"));
    }

    /**
     * Method that performs state update if the elixir has been collected
     */
    public boolean update(Sailor sailor, int itemsCollected) {

        // if the elixir has been collected
        if (collected == true) {
            ICON.drawFromTopLeft(1, getItemNumber() * ICON_OFFSET + ICON_OFFSET);
            return false;
        }

        // the sailor collects the elixir
        if (ENTITY_BOX.intersects(sailor.getCharacterBox())) {
            sailor.setMaxHealthPoints(sailor.getMaxHealthPoints() + MAX_HEALTH_INCREASE); // increase max health
            sailor.setHealthPoints(sailor.getMaxHealthPoints()); // increase health to be max health
            System.out.println("Sailor finds Potion. Sailor's current health: " + sailor.getHealthPoints() + "/" + sailor.getMaxHealthPoints());
            collected = true;
            setItemNumber(itemsCollected);
            return true;
        }

        ENTITY_IMAGE.drawFromTopLeft(X, Y);
        return false;
    }
}