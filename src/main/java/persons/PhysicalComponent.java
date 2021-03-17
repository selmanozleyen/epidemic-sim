package persons;


public class PhysicalComponent implements IPhysicalComponent {
    private final double speed;
    @Override
    public double getSpeed() {
        return speed;
    }
    @Override
    public double getThickness() {
        return 5;
    }
    public PhysicalComponent(double speed) {
        this.speed = speed;
    }
}
