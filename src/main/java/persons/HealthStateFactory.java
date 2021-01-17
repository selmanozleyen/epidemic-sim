package persons;

import javafx.scene.shape.Circle;

public class HealthStateFactory implements IHealthStateFactory{
    @Override
    public IHealthState createHealthState(IHealthComponent hc, Circle socialCollider) {
        return new HealthState(hc,socialCollider);
    }
}
