import bagel.*;

import bagel.util.Point;
import bagel.util.Rectangle;

public class Level {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;

    private final int WIN_X_LEVEL_0 = 990;
    private final int WIN_Y_LEVEL_0 = 630;

    private int levelNumber;
    private Image background;
    private String worldFile;

    // edges of the level
    private int bottomEdge;
    private int topEdge;
    private int leftEdge;
    private int rightEdge;

    // rectangle class for the ladder/treasure
    private Rectangle goal = new Rectangle(WIN_X_LEVEL_0, WIN_Y_LEVEL_0, WINDOW_WIDTH-WIN_X_LEVEL_0, WINDOW_HEIGHT-WIN_Y_LEVEL_0);

    public final String START_MESSAGE = "PRESS SPACE TO START";

    public final String INSTRUCTION_MESSAGE_1 = "PRESS S TO ATTACK";
    private String instructionMessage2;

    public final String END_MESSAGE = "GAME OVER";

    private String winMessage;

    public final int INSTRUCTION_OFFSET = 70;

    public Level() {
        levelNumber = 0;
        instructionMessage2 = "USE ARROW KEYS TO FIND LADDER";
        winMessage = "LEVEL COMPLETE!";
        worldFile = "res/level0.csv";
    }

    /**
     * Method that updates the level background depending on the level number
     */
    public void setBackground(int levelNumber) {
        background = new Image("res/background" + levelNumber + ".png");
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public Image getBackground() {
        return background;
    }

    public String getInstructionMessage2() {
        return instructionMessage2;
    }

    public String getWinMessage() {
        return winMessage;
    }

    /**
     * Method that updates the attributes depending on the level number
     */
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
        setBackground(levelNumber);
        setWorldFile(levelNumber);
        setWinMessage(levelNumber);
    }

    public void setInstructionMessage2(String instructionMessage2) {
        this.instructionMessage2 = instructionMessage2;
    }

    /**
     * Method that sets the win messsage depending on the level number
     */
    public void setWinMessage(int levelNumber) {
        if (levelNumber == 0) {
            winMessage = "LEVEL COMPLETE!";
        } else {
            winMessage = "CONGRATULATIONS!";
        }
    }

    public String getWorldFile() {
        return worldFile;
    }

    /**
     * Method that updates the level world file depending on the level number
     */
    public void setWorldFile(int levelNumber) {
        worldFile = "res/level" + levelNumber + ".csv";
    }

    public int getBottomEdge() {
        return bottomEdge;
    }

    public int getTopEdge() {
        return topEdge;
    }

    public int getLeftEdge() {
        return leftEdge;
    }

    public int getRightEdge() {
        return rightEdge;
    }

    public void setBottomEdge(int bottomEdge) {
        this.bottomEdge = bottomEdge;
    }

    public void setTopEdge(int topEdge) {
        this.topEdge = topEdge;
    }

    public void setLeftEdge(int leftEdge) {
        this.leftEdge = leftEdge;
    }

    public void setRightEdge(int rightEdge) {
        this.rightEdge = rightEdge;
    }

    public Rectangle getGoal() {
        return goal;
    }

    public void setGoal(Rectangle goal) {
        this.goal = goal;
    }
}
