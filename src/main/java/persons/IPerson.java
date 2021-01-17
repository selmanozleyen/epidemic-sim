package persons;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;

public interface IPerson {
    Bounds getSocialBounds();

    IPhysicalState getPhysicalState();
    IHealthState getHealthState();

    boolean canTalk();

    boolean inSocialField(IPerson other);

    void update(ITown t, GraphicsContext context);
}
