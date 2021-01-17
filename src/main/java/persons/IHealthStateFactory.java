package persons;

import javafx.scene.shape.Circle;

public interface IHealthStateFactory {
    IHealthState createHealthState(IHealthComponent hc, Circle socialCollider);
}
