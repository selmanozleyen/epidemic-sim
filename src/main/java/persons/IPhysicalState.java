package persons;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;

public interface IPhysicalState {
    boolean isEnabled();

    void setEnabled(boolean enabled);

    Bounds getHitBounds();

    void update(ITown t, GraphicsContext context);

}
