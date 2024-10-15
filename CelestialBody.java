public class CelestialBody {
    private String name;
    private double gravity;
    private double originalGravity;

    public CelestialBody(String name, double gravity, double originalGravity) {
        this.name = name;
        this.gravity = gravity;
        this.originalGravity = originalGravity;
    }

    public String getName() { return name; }
    public double getGravity() { return gravity; }
    public double getOriginalGravity() { return originalGravity; }

    // Gravity multiplier to make effects more noticeable
    private static final double GRAVITY_MULTIPLIER = 1.5 * (1 / 24.79);

    public static final CelestialBody[] BODIES = {
        new CelestialBody("Deimos", 0.003 * GRAVITY_MULTIPLIER, 0.003),
        new CelestialBody("Phobos", 0.0057 * GRAVITY_MULTIPLIER, 0.0057),
        new CelestialBody("Mimas", 0.064 * GRAVITY_MULTIPLIER, 0.064),
        new CelestialBody("Proteus", 0.07 * GRAVITY_MULTIPLIER, 0.07),
        new CelestialBody("Miranda", 0.079 * GRAVITY_MULTIPLIER, 0.079),
        new CelestialBody("Enceladus", 0.113 * GRAVITY_MULTIPLIER, 0.113),
        new CelestialBody("Iapetus", 0.223 * GRAVITY_MULTIPLIER, 0.223),
        new CelestialBody("Charon", 0.288 * GRAVITY_MULTIPLIER, 0.288),
        new CelestialBody("Moon", 1.62 * GRAVITY_MULTIPLIER, 1.62),
        new CelestialBody("Triton", 0.779 * GRAVITY_MULTIPLIER, 0.779),
        new CelestialBody("Europa", 1.314 * GRAVITY_MULTIPLIER, 1.314),
        new CelestialBody("Callisto", 1.235 * GRAVITY_MULTIPLIER, 1.235),
        new CelestialBody("Titan", 1.352 * GRAVITY_MULTIPLIER, 1.352),
        new CelestialBody("Ganymede", 1.428 * GRAVITY_MULTIPLIER, 1.428),
        new CelestialBody("Io", 1.796 * GRAVITY_MULTIPLIER, 1.796),
        new CelestialBody("Mars", 3.71 * GRAVITY_MULTIPLIER, 3.71),
        new CelestialBody("Mercury", 3.7 * GRAVITY_MULTIPLIER, 3.7),
        new CelestialBody("Venus", 8.87 * GRAVITY_MULTIPLIER, 8.87),
        new CelestialBody("Uranus", 8.87 * GRAVITY_MULTIPLIER, 8.87),
        new CelestialBody("Earth", 9.81 * GRAVITY_MULTIPLIER, 9.81),
        new CelestialBody("Saturn", 10.44 * GRAVITY_MULTIPLIER, 10.44),
        new CelestialBody("Neptune", 11.15 * GRAVITY_MULTIPLIER, 11.15),
        new CelestialBody("Jupiter", 24.79 * GRAVITY_MULTIPLIER, 24.79)
    };
}
