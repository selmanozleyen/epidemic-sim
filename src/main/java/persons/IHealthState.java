package persons;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;

public interface IHealthState {
    double getTimeToDie();

    void setTimeToDie(double timeToDie);

    IHealthComponent getComponent();

    Bounds getSocialBounds();


    void update(Town t, GraphicsContext context, double x, double y);

    boolean isInfected();

    void setInfected(boolean infected);

    boolean isDead();
}
