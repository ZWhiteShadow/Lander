public class PhysicsEngine {
    private static final double MAX_SAFE_LANDING_SPEED = 2.0;
    private static final double MAX_SAFE_LANDING_ANGLE = 10.0;
    
    public void update(Lander lander, Terrain terrain) {
        lander.update();
        // Add more complex physics calculations here if needed
    }
    
    public boolean isLandingSafe(Lander lander) {
        double speed = Math.sqrt(Math.pow(lander.getVelocityX(), 2) + Math.pow(lander.getVelocityY(), 2));
        return speed <= MAX_SAFE_LANDING_SPEED && Math.abs(lander.getRotation()) <= MAX_SAFE_LANDING_ANGLE;
    }
    
    public int calculateCrashDamage(Lander lander) {
        double speed = Math.sqrt(Math.pow(lander.getVelocityX(), 2) + Math.pow(lander.getVelocityY(), 2));
        double angle = Math.abs(lander.getRotation());
        return (int)(speed * 5 + angle);
    }
}
