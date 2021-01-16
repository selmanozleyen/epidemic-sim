package persons;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Person {
    PhysicalState physicalState;
    HealthState healthState;
    public Person(PhysicalState physicalState, HealthState healthState){
        this.healthState = healthState;
        this.physicalState = physicalState;
    }

    boolean canTalk(){
        return physicalState.isEnabled();
    }
    boolean inSocialField(Person other){
        return other.healthState.getSocialBounds().intersects(other.healthState.getSocialBounds());
    }
    public void update(Town t,GraphicsContext context){
        physicalState.update(context,t);
    }

}
