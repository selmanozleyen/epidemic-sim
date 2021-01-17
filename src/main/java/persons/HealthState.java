package persons;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HealthState implements IHealthState {
    private final Circle socialCollider;

    @Override
    public double getTimeToDie() {
        return timeToDie;
    }

    @Override
    public void setTimeToDie(double timeToDie) {
        this.timeToDie = timeToDie;
    }

    private double timeToDie=60;
    private boolean dead = false;

    @Override
    public double getTimeToHospital() {
        return timeToHospital;
    }

    @Override
    public void setTimeToHospital(double timeToHospital) {
        this.timeToHospital = timeToHospital;
    }

    private double timeToHospital=25;
    private boolean infected = false;
    private final Logger LOG =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
    public void update(ITown t, GraphicsContext context, double x, double y) {
        socialCollider.setCenterX(x);
        socialCollider.setCenterY(y);
        if(infected){
            setTimeToDie(timeToDie-0.0166);
            setTimeToHospital(timeToHospital-0.0166);
            if (getTimeToDie() <= 0){
                LOG.log(Level.INFO,"DEATH");
                dead = true;
            }
        }
        if (infected){
            context.save();
            context.setStroke(Color.DARKOLIVEGREEN);
            context.strokeOval(
                    socialCollider.getCenterX()-socialCollider.getRadius()/2,
                    socialCollider.getCenterY()-socialCollider.getRadius()/2,
                    socialCollider.getRadius(),
                    socialCollider.getRadius()
            );
            context.restore();
        }
    }

    @Override
    public boolean isInfected() {
        return infected;
    }
    @Override
    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    @Override
    public boolean isDead() {
        return dead;
    }
}
