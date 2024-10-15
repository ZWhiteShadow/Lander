public class GameScreen {
    private Lander lander;
    private GameState gameState;
    
    // ... other fields ...

    public GameScreen() {
        // ... existing constructor code ...
        gameState = GameState.PLAYING;
    }

    public void render(float delta) {
        // ... existing render code ...

        if (gameState == GameState.PLAYING) {
            // Update lander
            lander.update();

            // Check for game over condition
            if (lander.isOffScreen()) {
                gameOver();
            }
        }

        // ... rest of the render method ...
    }

    private void gameOver() {
        gameState = GameState.GAME_OVER;
        System.out.println("Game Over! Lander is no longer visible in the window.");
        // Add any additional game over logic here
    }

    // ... rest of the class ...
}
