import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {
    private LanderGame game;
    
    public InputHandler(LanderGame game) {
        this.game = game;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (game.getGameState()) {
            case TITLE_SCREEN:
                game.setGameState(LanderGame.GameState.PLAYING);
                break;
            case PLAYING:
                handleGameInput(e.getKeyCode());
                break;
            case END_SCREEN:
                game.resetGame();
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (game.getGameState() == LanderGame.GameState.PLAYING) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                    game.getLander().stopRotation();
                    break;
                case KeyEvent.VK_UP:
                    game.getLander().stopThrust();
                    break;
            }
        }
    }
    
    private void handleGameInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                game.getLander().rotateLeft();
                break;
            case KeyEvent.VK_RIGHT:
                game.getLander().rotateRight();
                break;
            case KeyEvent.VK_UP:
                game.getLander().thrust();
                break;
        }
    }
}
