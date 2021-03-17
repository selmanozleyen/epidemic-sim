package persons;

import javafx.scene.shape.Rectangle;

public interface IPhysicalStateFactory {
    IPhysicalState createPhysicalState(IPhysicalComponent component, Rectangle hitCollider, double direction);
}
