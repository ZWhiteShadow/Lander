import java.awt.*;

public class GameRenderer {
    private LanderGame game;
    
    public GameRenderer(LanderGame game) {
        this.game = game;
    }
    
    public void render(Graphics2D g2d) {
        switch (game.getGameState()) {
            case TITLE_SCREEN:
                drawTitleScreen(g2d);
                break;
            case PLAYING:
                drawGame(g2d);
                break;
            case END_SCREEN:
                drawEndScreen(g2d);
                break;
        }
    }
    
    private void drawTitleScreen(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Courier", Font.BOLD, 48));
        g2d.drawString("LANDER", game.getWidth() / 2 - 100, game.getHeight() / 2 - 50);
        g2d.setFont(new Font("Courier", Font.PLAIN, 24));
        g2d.drawString("Press any key to begin", game.getWidth() / 2 - 120, game.getHeight() / 2 + 50);
    }
    
    private void drawGame(Graphics2D g2d) {
        game.getTerrain().draw(g2d);
        game.getLander().draw(g2d);
        drawUI(g2d);
    }
    
    private void drawEndScreen(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Courier", Font.BOLD, 48));
        g2d.drawString("Game Over", game.getWidth() / 2 - 120, game.getHeight() / 2 - 50);
        g2d.setFont(new Font("Courier", Font.PLAIN, 24));
        g2d.drawString("Score: " + game.getScoreManager().getScore(), game.getWidth() / 2 - 50, game.getHeight() / 2 + 20);
        g2d.drawString("Press any key to restart", game.getWidth() / 2 - 120, game.getHeight() / 2 + 70);
    }
    
    private void drawUI(Graphics2D g2d) {
        // Draw fuel
        g2d.setColor(Color.WHITE);
        g2d.drawString("Fuel: " + String.format("%.2f%%", game.getLander().getFuel()), 10, 20);
        
        // Draw health bar
        int healthBarWidth = 100;
        int healthBarHeight = 10;
        g2d.setColor(Color.RED);
        g2d.fillRect(game.getWidth() - healthBarWidth - 10, 10, healthBarWidth, healthBarHeight);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(game.getWidth() - healthBarWidth - 10, 10, (int)(healthBarWidth * game.getLander().getHealth() / 100), healthBarHeight);
        
        // Draw score
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + game.getScoreManager().getScore(), game.getWidth() / 2 - 30, 20);
        
        // Draw level info
        LevelManager levelManager = game.getLevelManager();
        CelestialBody currentBody = levelManager.getCurrentBody();
        g2d.drawString("Level: " + levelManager.getCurrentLevel(), 10, 40);
        g2d.drawString("Body: " + currentBody.getName(), 10, 60);
        g2d.drawString("Gravity: " + String.format("%.2f m/s²", currentBody.getGravity()), 10, 80);
    }
}
