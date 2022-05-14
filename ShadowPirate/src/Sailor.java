import bagel.*;
import bagel.util.Rectangle;

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

    private final static int WIN_X = 990;
    private final static int WIN_Y = 630;

    public Sailor(int startX, int startY) {
        // calls the constructor in the parent class
        super(new Image("res/sailor/sailorLeft.png"),
                new Image("res/sailor/sailorRight.png"),
                1,
                100,
                15,
                120,
                30,
                startX,
                startY,
                new Image("res/sailor/sailorRight.png"));
    }

    /**
     * Method that performs state update
     */
    public void update(Input input, Block[] blocks) {
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
            currentImage = MOVE_LEFT;
        } else if (input.isDown(Keys.RIGHT)) {
            setOldPoints();
            move(MOVE_SIZE,0);
            currentImage = MOVE_RIGHT;
        } else if (input.wasPressed(Keys.S)) {
            //
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
        // check collisions and print log

        // loop through blocks
        for (Block current : blocks) {
            if (getCharacterBox().intersects(current.getBlockBox())) {
                moveBack();
            }
        }
    }

    /**
     * Method that checks if sailor has gone out-of-bound
     */
    public void isOutOfBound() {
        if ((getCharacterBox().centre().y > BOTTOM_EDGE) || (getCharacterBox().centre().y < TOP_EDGE) || (getCharacterBox().centre().x < 0) ||
                (getCharacterBox().centre().x > Window.getWidth())) {
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
    public boolean hasWon() {
        return (getCharacterBox().centre().x >= WIN_X) && (getCharacterBox().centre().y > WIN_Y);
    }
}