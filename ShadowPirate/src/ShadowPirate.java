import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * SWEN20003 Project 2, Semester 1, 2022
 *
 * @author Dragan Stojanovski
 * Credits to Tharun Dharmawickrema for Project 1 Sample Solution
 */

public class ShadowPirate extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "ShadowPirate";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final static String WORLD_FILE = "res/level0.csv";
    private final static String START_MESSAGE = "PRESS SPACE TO START";
    private final static String INSTRUCTION_MESSAGE = "USE ARROW KEYS TO FIND LADDER";
    private final static String END_MESSAGE = "GAME OVER";
    private final static String WIN_MESSAGE = "CONGRATULATIONS!";

    private final static int INSTRUCTION_OFFSET = 70;
    private final static int FONT_SIZE = 55;
    private final static int FONT_Y_POS = 402;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);

    private final static int MAX_ARRAY_SIZE = 49;

    private final Block[] blocks = new Block[MAX_ARRAY_SIZE];

    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    private Sailor sailor;

    private boolean gameOn;
    private boolean gameEnd;
    private boolean gameWin;

    // constructor to set the start of the game
    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV(WORLD_FILE);
        gameWin = false;
        gameEnd = false;
        gameOn = false;
    }

    /**
     * Entry point for program
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();
    }

    /**
     * Method used to read file and create objects
     */
    private void readCSV(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

            int blockCount = 0;

            // loop through lines in the file
            while((line = reader.readLine()) != null) {
                String[] sections = line.split(",");

                if (sections[0].equals("Sailor")) {
                    sailor = new Sailor(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                }

                else if (sections[0].equals("Pirate")) {
                    enemies.add(new Enemy(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                            "pirate"));
                }

                else if (sections[0].equals("Block")) {
                    blocks[blockCount] = new Block(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                    blockCount++;
                }

                else if (sections[0].equals("Blackbeard")) {
                    enemies.add(new Enemy(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                            "blackbeard"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Performs a state update. Pressing escape key,
     * allows game to exit.
     */
    @Override
    public void update(Input input) {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        if (!gameOn) { // if the game has not started
            drawStartScreen(input);
        }

        if (gameEnd) { // if the player lost
            drawEndScreen(END_MESSAGE);
        }

        if (gameWin) { // if the player won
            drawEndScreen(WIN_MESSAGE);
        }

        if (gameOn && !gameEnd && !gameWin) { // when the game is running

            // update each block
            for (Block block : blocks) {
                block.update();
            }

            // update sailor
            sailor.update(input, blocks, enemies);

            // update each enemy
            for (Enemy enemy: enemies) {
                enemy.update(enemy.getEnemyMoveDirection(), blocks, sailor, projectiles);
            }

            // create a copy of the ArrayList
            ArrayList<Projectile> projectilesClone = (ArrayList<Projectile>) projectiles.clone();
            for (Projectile projectile: projectiles) {
                if (projectile.update(sailor) == true) {
                    projectilesClone.remove(projectile);
                }
            }
            projectiles = projectilesClone;

            // end the game if sailor is dead
            if (sailor.isDead()) {
                gameEnd = true;
            }

            // end the game is sailor has won
            if (sailor.hasWon()) {
                gameWin = true;
            }
        }
    }

    /**
     * Method used to draw the start screen instructions
     */
    private void drawStartScreen(Input input) {
        // draw start screen messages

        FONT.drawString(START_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(START_MESSAGE)/2.0)),
                FONT_Y_POS);
        FONT.drawString(INSTRUCTION_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(INSTRUCTION_MESSAGE)/2.0)),
                (FONT_Y_POS + INSTRUCTION_OFFSET));
        if (input.wasPressed(Keys.SPACE)) {
            // start game if user pressed SPACE
            gameOn = true;
        }
    }

    /**
     * Method used to draw end screen messages
     */
    private void drawEndScreen(String message) {
        // draw end screen message
        FONT.drawString(message, (Window.getWidth()/2.0 - (FONT.getWidth(message)/2.0)), FONT_Y_POS);
    }
}