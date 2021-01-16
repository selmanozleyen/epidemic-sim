package persons;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;


public class Person implements IPerson {
    IPhysicalState physicalState;
    IHealthState healthState;
    public Person(IPhysicalState physicalState, IHealthState healthState){
        this.healthState = healthState;
        this.physicalState = physicalState;
    }
    @Override
    public Bounds getSocialBounds(){
        return healthState.getSocialBounds();
    }
    @Override
    public boolean canTalk(){
        return physicalState.isEnabled();
    }
    @Override
    public boolean inSocialField(IPerson other){
        return this.getSocialBounds().intersects(other.getSocialBounds());
    }
    @Override
    public void update(Town t, GraphicsContext context){
        physicalState.update(t,context);
        healthState.update(t,context,physicalState.getHitCollider().getX(), physicalState.getHitCollider().getY());
    }
    @Override
    public IPhysicalState getPhysicalState() {
        return physicalState;
    }
    @Override
    public IHealthState getHealthState() {
        return healthState;
    }
}
