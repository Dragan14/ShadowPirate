import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.lang.Math;

public class Projectile {
    private final static int BOTTOM_EDGE = 670;
    private final static int TOP_EDGE = 60;

    private final String ENEMY_TYPE; // the type of enemy that fired the projectile
    private final int DAMAGE;
    private final Image PROJECTILE_IMAGE;

    private double x; // x position of the projectile
    private double y; // y position of the projectile
    private final double X_SPEED;
    private final double Y_SPEED;

    private final Point ENEMY_POINT;
    private final Point SAILOR_POINT;
    private double rotatation; // the rotation of the projectile in radians

    private boolean remove = false; // whether the projectile should be removed from the game

    private final double distance; // distance between the sailor and the enemy that fired the projectile
    private final double yDistance;
    private final double xDistance;

    public Projectile(String enemyType, Point ENEMY_POINT, Point SAILOR_POINT) {
        this.ENEMY_TYPE = enemyType;
        this.ENEMY_POINT = ENEMY_POINT;
        this.SAILOR_POINT = SAILOR_POINT;

        this.x = ENEMY_POINT.x;
        this.y = ENEMY_POINT.y;

        xDistance = SAILOR_POINT.x - ENEMY_POINT.x;
        yDistance = SAILOR_POINT.y - ENEMY_POINT.y;
        distance = (ENEMY_POINT).distanceTo(SAILOR_POINT);

        PROJECTILE_IMAGE = new Image("res/" + enemyType + "/" + enemyType + "Projectile.png");

        calculateRotation();

        if (enemyType.equals("pirate")) {
            // calculation for the x speed and y speed of the projectile
            X_SPEED = Math.abs(xDistance)/(xDistance) * Math.abs(Math.cos(rotatation)) * 0.4;
            Y_SPEED = Math.abs(yDistance)/(yDistance) * Math.abs(Math.sin(rotatation)) * 0.4;
            DAMAGE = 10;
        } else { // if the enemy is blackbeard
            // calculation for the x speed and y speed of the projectile
            X_SPEED = Math.abs(xDistance)/(xDistance) * Math.abs(Math.cos(rotatation)) * 0.8;
            Y_SPEED = Math.abs(yDistance)/(yDistance) * Math.abs(Math.sin(rotatation)) * 0.8;
            DAMAGE = 20;
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
            remove = true;
        } else if ((y > BOTTOM_EDGE) || (y < TOP_EDGE) || (x < 0) || (x > Window.getWidth())) {
            remove = true;
        }
    }
}
