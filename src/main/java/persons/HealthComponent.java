package persons;
public class HealthComponent implements IHealthComponent {
    private final double socialDistance;
    private final double socialTimeout;
    private final double maskCoefficient;

    @Override
    public double getSocialDistance() {
        return socialDistance;
    }
    @Override
    public double getSocialTimeout() {
        return socialTimeout;
    }
    @Override
    public double getMaskCoefficient() {
        return maskCoefficient;
    }

    public HealthComponent(double socialDistance, double socialTimeout, double maskCoefficient) {
        this.socialDistance = socialDistance;
        this.socialTimeout = socialTimeout;
        this.maskCoefficient = maskCoefficient;
    }
}
