import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Lander {
    private float x, y;
    private float width, height;
    private float screenWidth, screenHeight;
    private boolean isOffScreen;
    
    // Add these missing fields
    private float vx, vy;
    private float rotation;
    private float fuel;
    private float health;
    private float gravityStrength;
    private boolean thrusting;
    private float fuelUsed;
    private Point2D.Double velocity;

    // Constants
    private static final int THRUSTER_WIDTH = 5;
    private static final int THRUSTER_HEIGHT = 10;

    public static final int LANDER_SIZE = 20; // Adjust the value as needed

    public Lander(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        // Initialize lander position, size, etc.
        this.width = 20;  // Example width, adjust as needed
        this.height = 30; // Example height, adjust as needed
        this.x = screenWidth / 2 - width / 2;
        this.y = screenHeight - height;
        this.vx = 0;
        this.vy = 0;
        this.rotation = 0;
        this.fuel = 100;
        this.health = 100;
        this.fuelUsed = 0;
        this.thrusting = false;
        this.velocity = new Point2D.Double();
    }

    public void reset(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
        this.vx = 0;
        this.vy = 0;
        this.rotation = 0;
        this.fuel = 100;
        this.health = 100;
        this.fuelUsed = 0;
        this.thrusting = false;
        this.velocity = new Point2D.Double();
        // Reset any other necessary properties
    }
    
    public void setGravity(double gravity) {
        this.gravityStrength = (float) gravity;
    }
    
    public void update() {
        vy += gravityStrength * 0.1;
        
        if (thrusting && fuel > 0) {
            double thrustPower = 0.2;
            vx += Math.sin(Math.toRadians(rotation)) * thrustPower;
            vy -= Math.cos(Math.toRadians(rotation)) * thrustPower;
            fuel -= 0.5;
            fuelUsed += 0.5;
        }
        
        x += vx;
        y += vy;

        // Check if the lander is completely off the screen
        isOffScreen = x + width < 0 || x > screenWidth || y + height < 0 || y > screenHeight;
    }
    
    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(x, y);
        g2d.rotate(Math.toRadians(rotation));

        // Draw main triangle body
        g2d.setColor(Color.WHITE);
        int[] xPoints = {0, (int)(-width / 2), (int)(width / 2)};
        int[] yPoints = {(int)(-height), (int)(height / 2), (int)(height / 2)};
        drawPolygon(g2d, xPoints, yPoints);

        // Draw left thruster
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect((int)(-width / 2 - THRUSTER_WIDTH), (int)(height / 2 - THRUSTER_HEIGHT), THRUSTER_WIDTH, THRUSTER_HEIGHT);

        // Draw right thruster
        g2d.fillRect((int)(width / 2), (int)(height / 2 - THRUSTER_HEIGHT), THRUSTER_WIDTH, THRUSTER_HEIGHT);

        // Draw thrust flame if thrusting
        if (thrusting && fuel > 0) {
            g2d.setColor(Color.ORANGE);
            int[] flameX = {(int)(width / 4), 0, (int)(width / 4)};
            int[] flameY = {(int)(height / 2), (int)(height), (int)(height / 2)};
            drawPolygon(g2d, flameX, flameY);
        }

        g2d.setTransform(oldTransform);
    }
    
    public void rotateLeft() {
        rotation = Math.max(rotation - 5, -40);
    }
    
    public void rotateRight() {
        rotation = Math.min(rotation + 5, 40);
    }
    
    public void stopRotation() {
        // Do nothing, rotation stops immediately
    }
    
    public void thrust() {
        thrusting = true;
    }
    
    public void stopThrust() {
        thrusting = false;
    }
    
    public void damage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getRotation() { return rotation; }
    public double getFuel() { return fuel; }
    public double getHealth() { return health; }
    public double getFuelUsed() { return fuelUsed; }
    public Point2D.Double getVelocity() { return velocity; }
    public double getVelocityX() {
        return vx;
    }
    public double getVelocityY() {
        return vy;
    }
    
    public int getWidth() {
        return (int) width; // Assuming the lander is width pixels wide
    }
    
    public int getHeight() {
        return (int) height; // Assuming the lander is height pixels tall
    }

    public void setVelocity(double x, double y) {
        this.velocity.setLocation(x, y);
        this.vx = (float) x;
        this.vy = (float) y;
    }

    public boolean isOffScreen() {
        return isOffScreen;
    }

    private void drawPolygon(Graphics2D g2d, int[] xPoints, int[] yPoints) {
        int[] adjustedXPoints = new int[xPoints.length];
        int[] adjustedYPoints = new int[yPoints.length];
        for (int i = 0; i < xPoints.length; i++) {
            adjustedXPoints[i] = Math.round(xPoints[i]);
            adjustedYPoints[i] = Math.round(yPoints[i]);
        }
        g2d.fillPolygon(adjustedXPoints, adjustedYPoints, xPoints.length);
    }
}
