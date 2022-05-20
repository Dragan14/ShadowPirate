import bagel.*;
import bagel.util.Colour;
import bagel.util.Rectangle;

import java.util.ArrayList;

public abstract class Character {
    protected final Image MOVE_LEFT;
    protected final Image MOVE_RIGHT;

    protected final double MOVE_SIZE;

    private int maxHealthPoints;
    private int damagePoints;

    protected final int COOLDOWN;
    private int lastAttack;

    protected final static int ORANGE_BOUNDARY = 65;
    protected final static int RED_BOUNDARY = 35;
    private final int FONT_SIZE;
    protected final Font FONT;
    protected DrawOptions COLOUR = new DrawOptions();
    protected final static Colour GREEN = new Colour(0, 0.8, 0.2);
    protected final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    protected final static Colour RED = new Colour(1, 0, 0);

    private int healthPoints;

    private boolean facing = true; // false is facing left, true if facing right

    // store the top left coordinates of the character
    protected double x;
    protected double y;

    protected Image currentImage;

    private Rectangle characterBox;

    public Character(Image MOVE_LEFT, Image MOVE_RIGHT, double MOVE_SIZE, int maxHealthPoints, int damagePoints,
                     int COOLDOWN, int FONT_SIZE, int x, int y, Image currentImage) {
        this.MOVE_LEFT = MOVE_LEFT;
        this.MOVE_RIGHT = MOVE_RIGHT;
        this.MOVE_SIZE = MOVE_SIZE;
        this.maxHealthPoints = maxHealthPoints;
        this.damagePoints = damagePoints;
        this.COOLDOWN = COOLDOWN;
        this.lastAttack = COOLDOWN;
        this.FONT_SIZE = FONT_SIZE;
        this.FONT = new Font("res/wheaton.otf", FONT_SIZE);
        this.healthPoints = maxHealthPoints;
        this.x = x;
        this.y = y;
        this.currentImage = currentImage;

        characterBox = new Rectangle(x, y, currentImage.getWidth(), currentImage.getHeight());
        COLOUR.setBlendColour(GREEN);
    }

    /**
     * Method that moves the character given the direction
     */
    protected void move(double xMove, double yMove) {
        x += xMove;
        y += yMove;
        characterBox = new Rectangle(x, y, currentImage.getWidth(), currentImage.getHeight());
    }

    /**
     * Method that renders the current health as a percentage
     */
    public abstract void renderHealthPoints();

    /**
     * Method that checks if sailor's health is <= 0
     */
    public boolean isDead() {
        return healthPoints <= 0;
    }


    /**
     * Method that checks if sailor has gone out-of-bound
     */
    public abstract void isOutOfBound(Level level);

    /**
     * Method that determines the current image of the character depending on when they last attack
     */
    public abstract void setCurrentImage();

    public int getdamagePoints() {
        return damagePoints;
    }

    public int getLastAttack() {
        return lastAttack;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Rectangle getCharacterBox() {
        return characterBox;
    }

    public void setLastAttack(int lastAttack) {
        this.lastAttack = lastAttack;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setCharacterBox(Rectangle characterBox) {
        this.characterBox = characterBox;
    }

    public boolean getFacing() {
        return facing;
    }

    public void setFacing(boolean facing) {
        this.facing = facing;
    }

    public void setDamagePoints(int damagePoints) {
        this.damagePoints = damagePoints;
    }

    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }
}