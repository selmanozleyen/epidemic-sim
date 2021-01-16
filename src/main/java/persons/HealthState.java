package persons;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class HealthState implements IHealthState {
    private final Circle socialCollider;
    private float timeToDie;
    private float timeToGetInfected;
    private float timeToHospital;
    private final IHealthComponent component;

    public HealthState(IHealthComponent component,Circle socialCollider){
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

    @Override
    public void update(Town t, GraphicsContext context, double x, double y) {
        socialCollider.setCenterX(x);
        socialCollider.setCenterY(y);
        /*context.save();
        context.setStroke(Color.PURPLE);
        context.strokeOval(
                socialCollider.getCenterX()-socialCollider.getRadius()/2,
                socialCollider.getCenterY()-socialCollider.getRadius()/2,
                socialCollider.getRadius(),
                socialCollider.getRadius()
        );
        context.restore();*/
    }

}
