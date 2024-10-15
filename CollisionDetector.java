public class CollisionDetector {
    private Terrain terrain;

    public CollisionDetector(Terrain terrain) {
        this.terrain = terrain;
    }

    public boolean checkCollision(Lander lander) {
        int landerX = (int) lander.getX();
        int landerY = (int) lander.getY();
        int landerWidth = lander.getWidth();
        int landerHeight = lander.getHeight();

        // Check collision with terrain
        for (int x = landerX; x < landerX + landerWidth; x++) {
            if (x >= 0 && x < terrain.getWidth()) {
                int terrainHeight = terrain.getTerrainHeightAt(x);
                if (landerY + landerHeight > terrain.getHeight() - terrainHeight) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkSuccessfulLanding(Lander lander) {
        if (!terrain.isLandingPad(lander.getX())) {
            return false;
        }

        double verticalSpeed = Math.abs(lander.getVelocityY());
        double horizontalSpeed = Math.abs(lander.getVelocityX());

        // Define safe landing speeds
        final double MAX_VERTICAL_SPEED = 2.0;
        final double MAX_HORIZONTAL_SPEED = 1.0;

        return verticalSpeed <= MAX_VERTICAL_SPEED && horizontalSpeed <= MAX_HORIZONTAL_SPEED;
    }
}
