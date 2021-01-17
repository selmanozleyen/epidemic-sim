package persons;

import javafx.scene.shape.Rectangle;

public class PhysicalStateFactory implements IPhysicalStateFactory{

    @Override
    public IPhysicalState createPhysicalState(IPhysicalComponent component, Rectangle hitCollider, double direction) {
        return new PhysicalState(component,hitCollider,direction);
    }
}
