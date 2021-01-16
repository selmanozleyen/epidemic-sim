package persons;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;

public interface IHealthState {
    IHealthComponent getComponent();

    Bounds getSocialBounds();

    void update(Town t, GraphicsContext context, double x, double y);
}
