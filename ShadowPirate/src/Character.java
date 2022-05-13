import bagel.*;
import bagel.util.Colour;
import bagel.util.Rectangle;

public abstract class Character {
    private final Image MOVE_LEFT;
    private final Image MOVE_RIGHT;

    private final int MOVE_SIZE;

    private final int MAX_HEALTH_POINTS;
    private final int DAMAGE_POINTS;

    private final int COOLDOWN;
    private int lastAttack;

    protected final static int BOTTOM_EDGE = 670;
    protected final static int TOP_EDGE = 60;

    private int healthX;
    private int healthY;
    private final static int ORANGE_BOUNDARY = 65;
    private final static int RED_BOUNDARY = 35;
    private final int FONT_SIZE;
    private final Font FONT;
    private final DrawOptions COLOUR = new DrawOptions();
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);

    private int healthPoints;

    // points store the top left position
    private int oldX;
    private int oldY;
    private int x;
    private int y;

    private Image currentImage;

    private Rectangle characterBox;
    private Rectangle oldCharacterBox;

    // constructor to set value of variables and constants
    public Character(Image MOVE_LEFT, Image MOVE_RIGHT, int MOVE_SIZE, int MAX_HEALTH_POINTS, int DAMAGE_POINTS, int COOLDOWN, int healthX, int healthY, int FONT_SIZE, int x, int y, Image currentImage) {
        this.MOVE_LEFT = MOVE_LEFT;
        this.MOVE_RIGHT = MOVE_RIGHT;
        this.MOVE_SIZE = MOVE_SIZE;
        this.MAX_HEALTH_POINTS = MAX_HEALTH_POINTS;
        this.DAMAGE_POINTS = DAMAGE_POINTS;
        this.COOLDOWN = COOLDOWN;
        this.lastAttack = 0;
        this.healthX = healthX;
        this.healthY = healthY;
        this.FONT_SIZE = FONT_SIZE;
        this.FONT = new Font("res/wheaton.otf", FONT_SIZE);
        this.healthPoints = MAX_HEALTH_POINTS;
        this.x = x;
        this.y = y;
        this.currentImage = currentImage;

        characterBox = new Rectangle(x, y, currentImage.getWidth(), currentImage.getHeight());
        COLOUR.setBlendColour(GREEN);
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
        }
        currentImage.drawFromTopLeft(x, y);
        checkCollisions(blocks);
        isOutOfBound();
        renderHealthPoints();
    }

    /**
     * Method that checks for collisions between sailor and blocks
     */
    public abstract void checkCollisions(Block[] blocks);

    /**
     * Method that moves the sailor given the direction
     */
    private void move(int xMove, int yMove) {
        x += xMove;
        y += yMove;
        characterBox = new Rectangle(x, y, currentImage.getWidth(), currentImage.getHeight());
    }

    /**
     * Method that stores the old coordinates of the sailor
     */
    private void setOldPoints() {
        oldX = x;
        oldY = y;
        oldCharacterBox = new Rectangle(characterBox);
    }

    /**
     * Method that moves the sailor back to its previous position
     */
    public abstract void moveBack();

    /**
     * Method that renders the current health as a percentage on screen
     */
    private void renderHealthPoints() {
        double percentageHP = ((double) healthPoints/MAX_HEALTH_POINTS) * 100;
        if (percentageHP <= RED_BOUNDARY) {
            COLOUR.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY) {
            COLOUR.setBlendColour(ORANGE);
        }
        FONT.drawString(Math.round(percentageHP) + "%", healthX, healthY, COLOUR);
    }

    /**
     * Method that checks if sailor's health is <= 0
     */
    public boolean isDead() {
        return healthPoints <= 0;
    }


    /**
     * Method that checks if sailor has gone out-of-bound
     */
    public abstract void isOutOfBound();

    public Image getMOVE_LEFT() {
        return MOVE_LEFT;
    }

    public Image getMOVE_RIGHT() {
        return MOVE_RIGHT;
    }

    public int getMOVE_SIZE() {
        return MOVE_SIZE;
    }

    public int getMAX_HEALTH_POINTS() {
        return MAX_HEALTH_POINTS;
    }

    public int getDAMAGE_POINTS() {
        return DAMAGE_POINTS;
    }

    public int getCOOLDOWN() {
        return COOLDOWN;
    }

    public int getLastAttack() {
        return lastAttack;
    }

    public int getHealthX() {
        return healthX;
    }

    public int getHealthY() {
        return healthY;
    }

    public int getFONT_SIZE() {
        return FONT_SIZE;
    }

    public Font getFONT() {
        return FONT;
    }

    public DrawOptions getCOLOUR() {
        return COLOUR;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public Rectangle getCharacterBox() {
        return characterBox;
    }

    public Rectangle getOldCharacterBox() {
        return oldCharacterBox;
    }

    public void setLastAttack(int lastAttack) {
        this.lastAttack = lastAttack;
    }

    public void setHealthX(int healthX) {
        this.healthX = healthX;
    }

    public void setHealthY(int healthY) {
        this.healthY = healthY;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public void setCharacterBox(Rectangle characterBox) {
        this.characterBox = characterBox;
    }

    public void setOldCharacterBox(Rectangle oldCharacterBox) {
        this.oldCharacterBox = oldCharacterBox;
    }
}