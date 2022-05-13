import bagel.*;
import bagel.util.Rectangle;

public class Sailor extends Character{
    private final Image HIT_LEFT = new Image("res/sailor/sailorHitLeft.png");
    private final Image HIT_RIGHT = new Image("res/sailor/sailorHitRight.png");

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
                10,
                25,
                30,
                startX,
                startY,
                new Image("res/sailor/sailorRight.png"));
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
            System.out.println("here");
            moveBack();
        }
    }

    /**
     * Method that moves the sailor back to its previous position
     */
    public void moveBack() {
        setX(getOldX());
        setY(getOldY());
        setCharacterBox(new Rectangle(getOldCharacterBox()));
    }

    /**
     * Method that checks if sailor has reached the ladder
     */
    public boolean hasWon() {
        return (getCharacterBox().centre().x >= WIN_X) && (getCharacterBox().centre().y > WIN_Y);
    }
}