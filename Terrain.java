import java.awt.*;
import java.util.Random;

public class Terrain {
    private int width;
    private int height;
    private int[] terrainPoints;
    private int landingPadLeft;
    private int landingPadRight;
    private static final int LANDING_PAD_WIDTH = 60; // Adjust as needed

    public Terrain(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public void generateTerrain(String planetName) {
        Random random = new Random();
        terrainPoints = new int[width];
        
        // Generate flatter terrain at the bottom
        int baseHeight = height / 6; // Lower base height
        int previousHeight = baseHeight;
        
        for (int i = 0; i < width; i++) {
            int variation = random.nextInt(11) - 5; // Range from -5 to 5
            int newHeight = Math.max(height / 12, Math.min(height / 4, previousHeight + variation));
            terrainPoints[i] = newHeight;
            previousHeight = newHeight;
        }
        
        // Calculate average terrain height
        int totalHeight = 0;
        for (int height : terrainPoints) {
            totalHeight += height;
        }
        int averageHeight = totalHeight / width;
        
        // Generate landing pad
        int landingPadCenter = (int) (Math.random() * (width - LANDING_PAD_WIDTH)) + LANDING_PAD_WIDTH / 2;
        landingPadLeft = landingPadCenter - LANDING_PAD_WIDTH / 2;
        landingPadRight = landingPadCenter + LANDING_PAD_WIDTH / 2;
        int landingPadHeight = averageHeight + random.nextInt(21) - 10; // +/- 10 pixels from average

        // Create flat landing pad
        for (int x = landingPadLeft; x <= landingPadRight; x++) {
            terrainPoints[x] = landingPadHeight;
        }

        // Smooth terrain around landing pad
        smoothTerrainAroundLandingPad();
    }

    private void smoothTerrainAroundLandingPad() {
     
    }

    public boolean checkCollision(Lander lander) {
        int landerX = (int) lander.getX();
        int landerY = (int) lander.getY();
        int landerWidth = Lander.LANDER_SIZE;
        int landerHeight = Lander.LANDER_SIZE * 3 / 2;

        // Check if any corner of the lander is below the terrain
        for (int x = landerX; x <= landerX + landerWidth; x += landerWidth) {
            for (int y = landerY; y <= landerY + landerHeight; y += landerHeight) {
                if (y >= getHeight() - getTerrainHeightAt(x)) {
                    return true; // Collision detected
                }
            }
        }

        return false; // No collision
    }

    public int getTerrainHeightAt(double x) {
        int index = (int) Math.round(x);
        if (index >= 0 && index < terrainPoints.length) {
            return terrainPoints[index];
        }
        return 0; // Default height if x is out of bounds
    }

    public boolean isLandingPad(double x) {
        int xInt = (int) x;
        return xInt >= landingPadLeft && xInt <= landingPadRight;
    }

    public void draw(Graphics2D g2d) {
        // Draw the terrain
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < terrainPoints.length - 1; i++) {
            g2d.drawLine(i, height - terrainPoints[i], i + 1, height - terrainPoints[i + 1]);
        }

        // Draw landing pad as a green line
        g2d.setColor(Color.GREEN);
        int padHeight = terrainPoints[landingPadLeft];
        g2d.setStroke(new BasicStroke(2)); // Set line thickness to 2 pixels
        g2d.drawLine(landingPadLeft, height - padHeight, landingPadRight, height - padHeight);
        
        // Reset stroke to default
        g2d.setStroke(new BasicStroke(1));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // ... (rest of the Terrain class methods)
}
