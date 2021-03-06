import bagel.*;
import bagel.util.Colour;
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

    // has the sailor attacked
    private boolean attacked;

    private Rectangle oldCharacterBox;

    public Sailor(int startX, int startY) {
        // calls the constructor in the parent class
        super(new Image("res/sailor/sailorLeft.png"),
                new Image("res/sailor/sailorRight.png"),
                1,
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
            int enemiesSize = ((ArrayList<Enemy>) enemies.clone()).size();
            // call attack multiple times so the sailor can attack multiple enemies that are overlapping
            for (int i=0;i<enemiesSize;i++) {
                attack(enemies);
            }
            if (attacked == true) {
                setLastAttack(-1);
            }
        }
        attacked = false;
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
            if (getLastAttack() >= COOLDOWN) {
                if ((getCharacterBox().intersects(enemy.getCharacterBox())) && (enemy.getInvincible() >= enemy.INVINCIBLE_COOLDOWN)) {
                    enemy.setHealthPoints(enemy.getHealthPoints() - getdamagePoints()); // reduce health of enemy
                    enemy.setInvincible(0);
                    if (enemy.getHealthPoints() <= 0) { // if enemy has 0 or less health they are removed from the game
                        enemies.remove(enemy);
                    }
                    System.out.println("Sailor inflicts " + getdamagePoints() + " damage points on "
                            + (enemy.ENEMY_TYPE.substring(0, 1).toUpperCase() + enemy.ENEMY_TYPE.substring(1))
                            + ". " + (enemy.ENEMY_TYPE.substring(0, 1).toUpperCase() + enemy.ENEMY_TYPE.substring(1))
                            + "???s current health: " + enemy.getHealthPoints() + "/" + enemy.getMaxHealthPoints());
                    return;
                }
                attacked = true;
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
                currentImage = MOVE_LEFT;
            } else {
                currentImage = MOVE_RIGHT;
            }
        }
    }

    /**
     * Method that checks if sailor has gone out-of-bound
     */
    public void isOutOfBound(Level level) {
        if ((getCharacterBox().centre().y > level.getBottomEdge()) || (getCharacterBox().centre().y < level.getTopEdge()) || (getCharacterBox().centre().x < level.getLeftEdge()) ||
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
        double percentageHP = ((double) getHealthPoints()/getMaxHealthPoints()) * 100;
        if (percentageHP <= RED_BOUNDARY) {
            COLOUR.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY) {
            COLOUR.setBlendColour(ORANGE);
        } else {
            COLOUR.setBlendColour(GREEN);
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