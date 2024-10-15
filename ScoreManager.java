import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreManager {
    private int score;
    private long startTime;
    private List<Integer> highScores;
    
    public ScoreManager() {
        this.score = 0;
        this.startTime = 0;
        this.highScores = new ArrayList<>();
    }
    
    public void resetForNewLevel() {
        startTime = System.currentTimeMillis();
    }
    
    public void update(Lander lander) {
        score -= (int)(lander.getFuelUsed() * 0.1);
    }
    
    public void calculateLandingScore(Lander lander, int level) {
        long landingTime = (System.currentTimeMillis() - startTime) / 1000;
        int timeBonus = Math.max(0, 1000 - (int)(landingTime * 10));
        int fuelBonus = (int)(lander.getFuel() * 10);
        int healthBonus = (int)(lander.getHealth() * 5);
        int levelBonus = level * 100;
        
        int landingScore = timeBonus + fuelBonus + healthBonus + levelBonus;
        score += landingScore;
    }
    
    public void applyDamagePenalty(int damage) {
        score -= damage * 10;
    }
    
    public int getScore() {
        return score;
    }

    public void resetScore() {
        this.score = 0;
    }

    public void setScore(int currentScore) {
        this.score = currentScore;
    }

    public void updateScore(int points) {
        this.score += points;
    }

    public List<Integer> getHighScores() {
        return new ArrayList<>(highScores);
    }

    public void addHighScore(int score) {
        highScores.add(score);
        Collections.sort(highScores, Collections.reverseOrder());
        if (highScores.size() > 10) {
            highScores = highScores.subList(0, 10);
        }
    }
}
