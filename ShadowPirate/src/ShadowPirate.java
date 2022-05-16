import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

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

    private final int FONT_SIZE = 55;
    private final int FONT_Y_POS = 402;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);

    private final int LEVEL_COMPLETE_TIME = 180; // the amount of frames the level complete is to rendered for
    private int levelCompleteCounter = 0; // counts number of the frames the level complete screen has been rendered for

    private final Image TREASURE_IMAGE = new Image("res/treasure.png");

    private static Level level = new Level();

    // array to store the blocks and bombs
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    private Sailor sailor;

    private boolean gameOn;
    private boolean gameEnd;
    private boolean gameWin;

    // constructor to set the start of the game
    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV(level.getWorldFile());
        gameWin = false;
        gameEnd = false;
        gameOn = false;
    }

    /**
     * Entry point for program
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        level.setBackground(0);
        game.run();
    }

    /**
     * Method used to read file and create objects
     */
    private void readCSV(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

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

                else if (sections[0].equals("Blackbeard")) {
                    enemies.add(new Enemy(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                            "blackbeard"));
                }

                else if (sections[0].equals("Block")) {
                    if (level.getLevelNumber() == 0) {
                        entities.add(new Block(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                    } else {
                        entities.add(new Bomb(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                    }
                }

                else if (sections[0].equals("TopLeft")) {
                    level.setLeftEdge(Integer.parseInt(sections[1]));
                    level.setTopEdge(Integer.parseInt(sections[2]));
                }

                else if (sections[0].equals("BottomRight")) {
                    level.setRightEdge(Integer.parseInt(sections[1]));
                    level.setBottomEdge(Integer.parseInt(sections[2]));
                }

                else if (sections[0].equals("Treasure")) {
                    level.setGoal(new Rectangle(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                            TREASURE_IMAGE.getWidth(), TREASURE_IMAGE.getHeight()));
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

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        if (!gameOn) { // if the game has not started
            drawStartScreen(input);
        }

        if (gameEnd) { // if the player lost
            drawEndScreen(level.END_MESSAGE);
        }

        if (gameWin) { // if the player won
            if (level.getLevelNumber() == 0) {
                if (levelCompleteCounter < LEVEL_COMPLETE_TIME) {
                    drawEndScreen(level.getWinMessage());
                    levelCompleteCounter += 1;
                } else {
                    gameWin = false;
                    gameOn = false;
                    level.setLevelNumber(1);
                    enemies.clear();
                    projectiles.clear();
                    entities.clear();
                    readCSV(level.getWorldFile());
                }
            } else {
                drawEndScreen(level.getWinMessage());
            }
        }

        else if (gameOn && !gameEnd && !gameWin) { // when the game is running

            // render background
            level.getBackground().draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

            // render the sailor
            sailor.update(input, level, entities, enemies);

            // render each enemy
            for (Enemy enemy: enemies) {
                enemy.update(enemy.getEnemyMoveDirection(), level, entities, sailor, projectiles);
            }

            // update each entity
            ArrayList<Entity> entitiesClone = (ArrayList<Entity>) entities.clone();
            for (Entity entity : entities) {
                if(entity.update(sailor) == true) {
                    entitiesClone.remove(entity);
                }
            }
            entities = entitiesClone;

            if (level.getLevelNumber() == 1) {
                // render the treasure
                TREASURE_IMAGE.drawFromTopLeft(level.getGoal().topLeft().x, level.getGoal().topLeft().y);
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
            if (sailor.hasWon(level)) {
                gameWin = true;
            }
        }
    }

    /**
     * Method used to draw the start screen instructions
     */
    private void drawStartScreen(Input input) {
        // draw start screen messages

        FONT.drawString(level.START_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(level.START_MESSAGE)/2.0)),
                FONT_Y_POS);
        FONT.drawString(level.INSTRUCTION_MESSAGE_1, (Window.getWidth()/2.0 - (FONT.getWidth(level.INSTRUCTION_MESSAGE_1)/2.0)),
                (FONT_Y_POS + level.INSTRUCTION_OFFSET));
        FONT.drawString(level.getInstructionMessage2(), (Window.getWidth()/2.0 - (FONT.getWidth(level.getInstructionMessage2())/2.0)),
                (FONT_Y_POS + 2*level.INSTRUCTION_OFFSET));
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