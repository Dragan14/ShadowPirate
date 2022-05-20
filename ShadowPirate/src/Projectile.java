import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.lang.Math;

public class Projectile {
    private int BOTTOM_EDGE;
    private int TOP_EDGE;
    private int LEFT_EDGE;
    private int RIGHT_EDGE;

    private final String ENEMY_TYPE; // the type of enemy that fired the projectile
    private final int DAMAGE;
    private final Image PROJECTILE_IMAGE;

    private double x; // x position of the projectile
    private double y; // y position of the projectile
    private final double X_SPEED;
    private final double Y_SPEED;
    // (X_SPEED)^2 + (Y_SPEED)^2 = TOTAL SPEED

    private final double PIRATE_PROJECTILE_SPEED = 0.4;
    private final double BLACKBEARD_PROJECTILE_SPEED = 0.4;
    private final int PIRATE_DAMAGE = 10;
    private final int BLACKBEARD_DAMAGE = 20;

    private final Point ENEMY_POINT;
    private final Point SAILOR_POINT;
    private double rotatation; // the rotation of the projectile in radians

    private boolean remove = false; // whether the projectile should be removed from the game

    private final double distance; // distance between the sailor and the enemy that fired the projectile
    private final double yDistance;
    private final double xDistance;

    public Projectile(String enemyType, Point ENEMY_POINT, Point SAILOR_POINT, int TOP_EDGE, int BOTTOM_EDGE,
                      int LEFT_EDGE, int RIGHT_EDGE) {
        this.ENEMY_TYPE = enemyType;
        this.ENEMY_POINT = ENEMY_POINT;
        this.SAILOR_POINT = SAILOR_POINT;

        this.TOP_EDGE = TOP_EDGE;
        this.BOTTOM_EDGE = BOTTOM_EDGE;
        this.LEFT_EDGE = LEFT_EDGE;
        this.RIGHT_EDGE = RIGHT_EDGE;

        this.x = ENEMY_POINT.x;
        this.y = ENEMY_POINT.y;

        xDistance = SAILOR_POINT.x - ENEMY_POINT.x;
        yDistance = SAILOR_POINT.y - ENEMY_POINT.y;
        distance = (ENEMY_POINT).distanceTo(SAILOR_POINT);

        PROJECTILE_IMAGE = new Image("res/" + enemyType + "/" + enemyType + "Projectile.png");

        calculateRotation();

        if (enemyType.equals("pirate")) {
            // calculation for the x speed and y speed of the projectile
            X_SPEED = Math.abs(xDistance)/(xDistance) * Math.abs(Math.cos(rotatation)) * PIRATE_PROJECTILE_SPEED;
            Y_SPEED = Math.abs(yDistance)/(yDistance) * Math.abs(Math.sin(rotatation)) * PIRATE_PROJECTILE_SPEED;
            DAMAGE = PIRATE_DAMAGE;
        } else { // if the enemy is blackbeard
            // calculation for the x speed and y speed of the projectile
            X_SPEED = Math.abs(xDistance)/(xDistance) * Math.abs(Math.cos(rotatation)) * BLACKBEARD_PROJECTILE_SPEED;
            Y_SPEED = Math.abs(yDistance)/(yDistance) * Math.abs(Math.sin(rotatation)) * BLACKBEARD_PROJECTILE_SPEED;
            DAMAGE = BLACKBEARD_DAMAGE;
        }
    }

    /**
     * Method that performs state update, returns true if the projectile is to be removed from the game
     */
    public boolean update(Sailor sailor) {
        checkCollisions(sailor);
        if (remove == true) {
            return true;
        } else {
            move();
            PROJECTILE_IMAGE.draw(x,y,(new DrawOptions()).setRotation(rotatation));
        }
        return false;
    }

    /**
     * Method that calculates the rotation of the projectile in radians
     */
    private void calculateRotation() {
        rotatation = Math.acos(xDistance/distance);

        if (yDistance < 0) {
            rotatation = 2*Math.PI - rotatation;
        }
    }

    /**
     * Method that updates the x and y coordinates of the projectile
     */
    private void move() {
        x = x + X_SPEED;
        y = y + Y_SPEED;
    }

    /**
     * Method that returns whether a projectile has collided, and inflicts damage to the sailor
     */
    private void checkCollisions(Sailor sailor) {
        if (sailor.getCharacterBox().intersects(new Point(x,y))) {
            sailor.setHealthPoints(sailor.getHealthPoints() - DAMAGE); // inflict damage to the sailor
            System.out.println((ENEMY_TYPE.substring(0, 1).toUpperCase() + ENEMY_TYPE.substring(1)) + " inflicts " + DAMAGE
                    + " damage points on Sailor. Sailor’s current health: "
                    + sailor.getHealthPoints() + "/" + sailor.getMaxHealthPoints());
            remove = true;
        } else if ((y > BOTTOM_EDGE) || (y < TOP_EDGE) || (x < LEFT_EDGE) || (x > RIGHT_EDGE)) {
            remove = true;
        }
    }
}
