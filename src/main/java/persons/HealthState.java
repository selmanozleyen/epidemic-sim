package persons;

import javafx.geometry.Bounds;
import javafx.scene.shape.Shape;

public class HealthState implements IHealthState {
    private final Shape socialCollider;
    private float timeToDie;
    private float timeToGetInfected;
    private float timeToHospital;
    private final IHealthComponent component;

    public HealthState(IHealthComponent component,Shape socialCollider){
        this.component = component;
        this.socialCollider = socialCollider;
    }
    @Override
    public IHealthComponent getComponent(){
        return component;
    }
    @Override
    public Bounds getSocialBounds() {
        return socialCollider.getBoundsInLocal();
    }
}
