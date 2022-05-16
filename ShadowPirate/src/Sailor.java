import bagel.*;
import bagel.util.Rectangle;

import java.util.ArrayList;

public class Sailor extends Character {
    private final Image HIT_LEFT = new Image("res/sailor/sailorHitLeft.png");
    private final Image HIT_RIGHT = new Image("res/sailor/sailorHitRight.png");

    // the position of the health label
    private final int healthX = 10;
    private final int healthY = 25;

    // store the old top left coordinates
    private double oldX;
    private double oldY;

    private Rectangle oldCharacterBox;

    public Sailor(int startX, int startY) {
        // calls the constructor in the parent class
        super(new Image("res/sailor/sailorLeft.png"),
                new Image("res/sailor/sailorRight.png"),
                5,
                100,
                15,
                180,
                30,
                startX,
                startY,
                new Image("res/sailor/sailorRight.png"));
    }

    /**
     * Method that performs state update
     */
    public void update(Input input, Level level, ArrayList<Entity> entities, ArrayList<Enemy> enemies) {
        // store old coordinates every time the sailor moves
        if (input.isDown(Keys.UP)){
            setOldPoints();
            move(0, -MOVE_SIZE);
        } else if (input.isDown(Keys.DOWN)) {
            setOldPoints();
            move(0, MOVE_SIZE);
        } else if (input.isDown(Keys.LEFT)) {
            setOldPoints();
            move(-MOVE_SIZE,0);
            setFacing(false);
        } else if (input.isDown(Keys.RIGHT)) {
            setOldPoints();
            move(MOVE_SIZE,0);
            setFacing(true);
        } if (input.wasPressed(Keys.S)) {
            attack(enemies);
        }

        setCurrentImage();

        currentImage.drawFromTopLeft(x, y);
        isOutOfBound(level);
        renderHealthPoints();
        setLastAttack(getLastAttack() + 1);
    }

    /**
     * Method that attacks an enemy
     */
    private void attack(ArrayList<Enemy> enemies) {
        // loop through enemies
        for (Enemy enemy : enemies) {
            if (getCharacterBox().intersects(enemy.getCharacterBox())) {
                if ((getLastAttack() >= COOLDOWN) && (enemy.getInvincible() >= enemy.INVINCIBLE_COOLDOWN)) {
                    enemy.setHealthPoints(enemy.getHealthPoints() - getDAMAGE_POINTS()); // reduce health of enemy
                    setLastAttack(-1);
                    enemy.setInvincible(0);
                    if (enemy.getHealthPoints() <= 0) { // if enemy has 0 or less health they are removed from the game
                        enemies.remove(enemy);
                    }
                    return; // the player can only attack one enemy at a time
                }
            }
        }
    }

    /**
     * Method that determines the current image of the character depending on when they last attack
     */
    public void setCurrentImage() {
        if (getLastAttack() <= 60) { // if the player has attacked in the last 1000ms
            if (getFacing() == false) {
                currentImage = HIT_LEFT;
            } else {
                currentImage = HIT_RIGHT;
            }
        } else {
            if (getFacing() == false) {
                currentImage = getMOVE_LEFT();
            } else {
                currentImage = getMOVE_RIGHT();
            }
        }
    }

    /**
     * Method that checks if sailor has gone out-of-bound
     */
    public void isOutOfBound(Level level) {

        if ((getCharacterBox().topLeft().y > level.getBottomEdge()) || (getCharacterBox().topLeft().y < level.getTopEdge()) || (getCharacterBox().centre().x < level.getLeftEdge()) ||
                (getCharacterBox().centre().x > level.getRightEdge())) {
            moveBack();
        }

    }

    /**
     * Method that stores the old coordinates of the sailor
     */
    private void setOldPoints() {
        oldX = getX();
        oldY = getY();
        oldCharacterBox = new Rectangle(getCharacterBox());
    }

    /**
     * Method that moves the sailor back to its previous position
     */
    public void moveBack() {
        setX(oldX);
        setY(oldY);
        setCharacterBox(new Rectangle(oldCharacterBox));
    }

    /**
     * Method that renders the current health as a percentage on screen
     */
    public void renderHealthPoints() {
        double percentageHP = ((double) getHealthPoints()/getMAX_HEALTH_POINTS()) * 100;
        if (percentageHP <= RED_BOUNDARY) {
            COLOUR.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY) {
            COLOUR.setBlendColour(ORANGE);
        }
        FONT.drawString(Math.round(percentageHP) + "%", healthX, healthY, COLOUR);
    }

    /**
     * Method that checks if sailor has reached the ladder
     */
    public boolean hasWon(Level level) {
        return getCharacterBox().intersects(level.getGoal());
    }
}