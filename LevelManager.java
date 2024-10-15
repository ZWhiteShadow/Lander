import java.util.Arrays;
import java.util.Comparator;

public class LevelManager {
    private CelestialBody[] bodies;
    private int currentLevelIndex;
    private int currentLevel;

    public LevelManager() {
        // Directly assign the BODIES array
        bodies = CelestialBody.BODIES;

        // Sort celestial bodies by gravity, from lowest to highest
        Arrays.sort(bodies, Comparator.comparingDouble(CelestialBody::getGravity));

        currentLevelIndex = 0;
        currentLevel = 0;
    }

    public CelestialBody getCurrentBody() {
        return bodies[currentLevelIndex];
    }

    public void nextLevel() {
        if (currentLevelIndex < bodies.length - 1) {
            currentLevelIndex++;
        }
    }

    public boolean isGameComplete() {
        return currentLevelIndex == bodies.length - 1;
    }

    public int getCurrentLevelNumber() {
        return currentLevelIndex + 1;
    }

    public int getTotalLevels() {
        return bodies.length;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setLevel(int level) {
        this.currentLevel = level;
    }
}
