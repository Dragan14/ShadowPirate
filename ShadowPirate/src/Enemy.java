import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Rectangle;
import java.util.Random;
import java.lang.Math;

public class Enemy extends Character {
    private final Image INVINCIBLE_LEFT;
    private final Image INVINCIBLE_RIGHT;
    private final String ENEMY_TYPE; // blackbeard or pirate
    private final Rectangle ATTACK_RANGE;
    private int moveDir = (new Random()).nextInt(0,4); // random integer between 0 and 3 inclusive

    public Enemy(int startX, int startY, String ENEMY_TYPE) {
        // calls the constructor in the parent class

        super(new Image("res/" + ENEMY_TYPE + "/" + ENEMY_TYPE + "Left.png"),
                new Image("res/" + ENEMY_TYPE + "/" + ENEMY_TYPE + "Right.png"),
                (new Random()).nextDouble(0.2,0.7),
                toBlackbeardValue(45, true, ENEMY_TYPE),
                toBlackbeardValue(10, true, ENEMY_TYPE),
                toBlackbeardValue(3000, false, ENEMY_TYPE),
                15,
                startX,
                startY,
                new Image("res/" + ENEMY_TYPE + "/" + ENEMY_TYPE + "Right.png"));

        this.ENEMY_TYPE = ENEMY_TYPE;

        INVINCIBLE_LEFT = new Image("res/" + ENEMY_TYPE + "/" + ENEMY_TYPE + "HitLeft.png");

        INVINCIBLE_RIGHT = new Image("res/" + ENEMY_TYPE + "/" + ENEMY_TYPE + "HitRight.png");

        ATTACK_RANGE = new Rectangle(startX, startY, 100, 100);
    }

    /**
     * Method that performs state update
     */
    public void update(Keys input, Block[] blocks) {
        // store old coordinates every time the sailor moves
        if (input.equals(Keys.UP)){
            move(0, -MOVE_SIZE);
        } else if (input.equals(Keys.DOWN)) {
            move(0, MOVE_SIZE);
        } else if (input.equals(Keys.LEFT)) {
            move(-MOVE_SIZE,0);
            currentImage = MOVE_LEFT;
        } else if (input.equals(Keys.RIGHT)) {
            move(MOVE_SIZE,0);
            currentImage = MOVE_RIGHT;
        }
        currentImage.drawFromTopLeft(x, y);
        checkCollisions(blocks);
        isOutOfBound();
        renderHealthPoints();
    }

    /**
     * Method that checks for collisions between sailor and blocks
     */
    public void checkCollisions(Block[] blocks) {

        // loop through blocks
        for (Block current : blocks) {
            // check collisions
            if (getCharacterBox().intersects(current.getBlockBox())) {
                reverseDirection();
                return;
            }
        }
    }

    /**
     * Method that checks if sailor has gone out-of-bound
     */
    public void isOutOfBound() {
        if ((getCharacterBox().centre().y > BOTTOM_EDGE) || (getCharacterBox().centre().y < TOP_EDGE) || (getCharacterBox().centre().x < 0) ||
                (getCharacterBox().centre().x > Window.getWidth())) {
            reverseDirection();
        }
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
        FONT.drawString(Math.round(percentageHP) + "%", getX(), getY() - 6,
                COLOUR);
    }

    /**
     * Method that doubles or halves an integer value if the enemy is blackbeard
     */
    private static int toBlackbeardValue(int value, boolean doubleValue, String enemyType) {
        if (enemyType.equals("blackbeard")) {
            if (doubleValue == true) {
                return value*2;
            } else {
                return (int) (value * (0.5));
            }
        }
        return value;
    }


    /**
     * Method that reverses the direction in which the enemy is moving
     */
    private void reverseDirection() {
        if (moveDir < 2) {
            moveDir = moveDir + 2;
        } else {
            moveDir = moveDir - 2;
        }
    }

    /**
     * Method that returns the direction in which the enemy is moving
     */
    public Keys getEnemyMoveDirection() {
        if (moveDir == 0) {
            return Keys.UP;
        }
        if (moveDir == 2) {
            return Keys.DOWN;
        }
        if (moveDir == 1) {
            return Keys.LEFT;
        }
        if (moveDir == 3) {
            return Keys.RIGHT;
        }
        return null;
    }
}
