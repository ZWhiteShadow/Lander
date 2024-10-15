import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LanderGame extends JPanel implements KeyListener, ActionListener {
    // Constants
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int FPS = 60;
    
    // Game objects
    private Lander lander;
    private Terrain terrain;
    
    // Game state
    private GameState gameState;
    private int currentLevel;
    private int score;
    
    // Timer for game loop
    private Timer timer;
    
    private CelestialBody currentBody;
    
    private boolean showExplosion;
    private long explosionStartTime;
    private static final long EXPLOSION_DURATION = 2000; // 2 seconds
    
    private ScoreManager scoreManager;
    private LevelManager levelManager;
    
    // Add this enum definition at the top of the class
    public enum GameState {
        TITLE_SCREEN, PLAYING, END_SCREEN
    }

    public LanderGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        
        scoreManager = new ScoreManager();
        levelManager = new LevelManager();
        
        initGame();
    }
    
    public void initGame() {
        gameState = GameState.TITLE_SCREEN;
        currentLevel = 1;
        
        if (lander == null) {
            lander = new Lander(WIDTH / 2, HEIGHT / 4);
        } else {
            lander.reset(WIDTH / 2, HEIGHT / 4);
        }
        
        terrain = new Terrain(WIDTH, HEIGHT);
        
        scoreManager.resetScore();
        levelManager.setLevel(1);
        
        setLevelEnvironment();
        
        if (timer == null) {
            timer = new Timer(1000 / FPS, this);
            timer.start();
        }
    }
    
    private void setLevelEnvironment() {
        currentBody = levelManager.getCurrentBody();
        lander.setGravity(currentBody.getGravity());
        terrain.generateTerrain(currentBody.getName());
        // Add atmosphere effects if needed
        // lander.setAtmosphereDensity(currentBody.getAtmosphereDensity());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        switch (gameState) {
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
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        g2d.drawString("LANDER", WIDTH / 2 - 100, HEIGHT / 2 - 50);
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        g2d.drawString("Press any key to begin", WIDTH / 2 - 120, HEIGHT / 2 + 50);
    }
    
    private void drawGame(Graphics2D g2d) {
        terrain.draw(g2d);
        if (!showExplosion) {
            lander.draw(g2d);
        } else {
            drawExplosion(g2d);
        }
        drawUI(g2d);
    }
    
    private void drawEndScreen(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        g2d.drawString("Game Over", WIDTH / 2 - 120, HEIGHT / 2 - 50);
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        g2d.drawString("Score: " + score, WIDTH / 2 - 50, HEIGHT / 2 + 20);
        g2d.drawString("Press any key to restart", WIDTH / 2 - 120, HEIGHT / 2 + 70);
    }
    
    private void drawUI(Graphics2D g2d) {
        // Draw fuel
        g2d.setColor(Color.WHITE);
        g2d.drawString("Fuel: " + String.format("%.2f%%", lander.getFuel()), 10, 20);
        
        // Draw health bar
        int healthBarWidth = 100;
        int healthBarHeight = 10;
        g2d.setColor(Color.RED);
        g2d.fillRect(WIDTH - healthBarWidth - 10, 10, healthBarWidth, healthBarHeight);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(WIDTH - healthBarWidth - 10, 10, (int)(healthBarWidth * lander.getHealth() / 100), healthBarHeight);
        
        // Draw score
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + score, WIDTH / 2 - 30, 20);
    }
    
    private void drawExplosion(Graphics2D g2d) {
        long elapsedTime = System.currentTimeMillis() - explosionStartTime;
        float progress = Math.min(1f, (float)elapsedTime / EXPLOSION_DURATION);
        
        int explosionSize = (int)(100 * progress);
        Color explosionColor = new Color(1f, 0.5f, 0f, 1f - progress);
        
        g2d.setColor(explosionColor);
        g2d.fillOval((int)lander.getX() - explosionSize/2, (int)lander.getY() - explosionSize/2, explosionSize, explosionSize);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameState == GameState.PLAYING) {
            updateGame();
        }
        repaint();
    }
    
    private void updateGame() {
        lander.update();
        checkCollisions();
        updateScore();
        checkGameOver();
    }
    
    private void checkCollisions() {
        if (terrain.checkCollision(lander)) {
            double impactVelocity = Math.sqrt(Math.pow(lander.getVelocityX(), 2) + Math.pow(lander.getVelocityY(), 2));
            double impactAngle = Math.abs(lander.getRotation());
            
            if (terrain.isLandingPad(lander.getX())) {
                if (impactVelocity <= 2 && impactAngle <= 10) {
                    handleSuccessfulLanding();
                } else {
                    handleCrash(impactVelocity, impactAngle);
                }
            } else {
                handleCrash(impactVelocity, impactAngle);
            }
        }
    }
    
    private void handleCrash(double impactVelocity, double impactAngle) {
        int damage = (int)(impactVelocity * 5 + impactAngle);
        lander.damage(damage);
        scoreManager.applyDamagePenalty(damage);
    }
    
    private void handleSuccessfulLanding() {
        scoreManager.calculateLandingScore(lander, currentLevel);
        
        // Display landing results
        JOptionPane.showMessageDialog(this, 
            "Successful landing!\n" +
            "Score: " + scoreManager.getScore());
        
        levelManager.nextLevel();
        
        if (levelManager.isGameComplete()) {
            gameState = GameState.END_SCREEN;
        } else {
            setLevelEnvironment();
            lander.reset(WIDTH / 2, HEIGHT / 4);
            terrain = new Terrain(WIDTH, HEIGHT);
            scoreManager.resetForNewLevel();
        }
    }
    
    private void updateScore() {
        scoreManager.update(lander);
    }
    
    private void checkGameOver() {
        if (lander.getHealth() <= 0) {
            showExplosion = true;
            explosionStartTime = System.currentTimeMillis();
            Timer explosionTimer = new Timer((int)EXPLOSION_DURATION, e -> {
                showExplosion = false;
                gameState = GameState.END_SCREEN;
                ((Timer)e.getSource()).stop();
            });
            explosionTimer.setRepeats(false);
            explosionTimer.start();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (gameState) {
            case TITLE_SCREEN:
                gameState = GameState.PLAYING;
                // Remove the following line:
                // startTime = System.currentTimeMillis();
                break;
            case PLAYING:
                handleGameInput(e.getKeyCode());
                break;
            case END_SCREEN:
                initGame();
                break;
        }
    }
    
    private void handleGameInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                lander.rotateLeft();
                break;
            case KeyEvent.VK_RIGHT:
                lander.rotateRight();
                break;
            case KeyEvent.VK_UP:
                lander.thrust();
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (gameState == GameState.PLAYING) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                    lander.stopRotation();
                    break;
                case KeyEvent.VK_UP:
                    lander.stopThrust();
                    break;
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Lander Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new LanderGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Lander getLander() {
        return lander;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setGameState(GameState newState) {
        this.gameState = newState;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void resetGame() {
        setGameState(GameState.TITLE_SCREEN);
        scoreManager.resetScore();
        levelManager.setLevel(1);
        lander.reset(WIDTH / 2, HEIGHT / 4);
        initGame();
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public List<Integer> getHighScores() {
        return scoreManager.getHighScores();
    }

    public void saveHighScore() {
        scoreManager.addHighScore(score);
    }
}
