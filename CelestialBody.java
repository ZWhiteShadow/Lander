public class CelestialBody {
    private String name;
    private double gravity;
    
    public CelestialBody(String name, double gravity) {
        this.name = name;
        this.gravity = gravity;
    }
    
    public String getName() { return name; }
    public double getGravity() { return gravity; }
    
    // Gravity multiplier to make effects more noticeable
    private static final double GRAVITY_MULTIPLIER = 10.0;
    
    public static final CelestialBody[] BODIES = {
        new CelestialBody("Deimos", 0.003 * GRAVITY_MULTIPLIER),
        new CelestialBody("Phobos", 0.0057 * GRAVITY_MULTIPLIER),
        new CelestialBody("Mimas", 0.064 * GRAVITY_MULTIPLIER),
        new CelestialBody("Proteus", 0.07 * GRAVITY_MULTIPLIER),
        new CelestialBody("Miranda", 0.079 * GRAVITY_MULTIPLIER),
        new CelestialBody("Enceladus", 0.113 * GRAVITY_MULTIPLIER),
        new CelestialBody("Iapetus", 0.223 * GRAVITY_MULTIPLIER),
        new CelestialBody("Charon", 0.288 * GRAVITY_MULTIPLIER),
        new CelestialBody("Moon", 1.62 * GRAVITY_MULTIPLIER),
        new CelestialBody("Triton", 0.779 * GRAVITY_MULTIPLIER),
        new CelestialBody("Europa", 1.314 * GRAVITY_MULTIPLIER),
        new CelestialBody("Callisto", 1.235 * GRAVITY_MULTIPLIER),
        new CelestialBody("Titan", 1.352 * GRAVITY_MULTIPLIER),
        new CelestialBody("Ganymede", 1.428 * GRAVITY_MULTIPLIER),
        new CelestialBody("Io", 1.796 * GRAVITY_MULTIPLIER),
        new CelestialBody("Mars", 3.71 * GRAVITY_MULTIPLIER),
        new CelestialBody("Mercury", 3.7 * GRAVITY_MULTIPLIER),
        new CelestialBody("Venus", 8.87 * GRAVITY_MULTIPLIER),
        new CelestialBody("Uranus", 8.87 * GRAVITY_MULTIPLIER),
        new CelestialBody("Earth", 9.81 * GRAVITY_MULTIPLIER),
        new CelestialBody("Saturn", 10.44 * GRAVITY_MULTIPLIER),
        new CelestialBody("Neptune", 11.15 * GRAVITY_MULTIPLIER),
        new CelestialBody("Jupiter", 24.79 * GRAVITY_MULTIPLIER)
    };
}
