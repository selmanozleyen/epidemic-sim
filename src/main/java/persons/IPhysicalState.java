package persons;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public interface IPhysicalState {
    boolean isEnabled();

    void setEnabled(boolean enabled);

    Bounds getHitBounds();

    Rectangle getHitCollider();

    void update(Town t, GraphicsContext context);

}
