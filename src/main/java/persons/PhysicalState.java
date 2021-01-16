package persons;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.concurrent.*;

public class PhysicalState implements IPhysicalState {
    private double direction;
    private final Rectangle hitCollider;
    private final IPhysicalComponent component;
    private boolean enabled = true;

    public PhysicalState(IPhysicalComponent component,Rectangle hitCollider, double direction) {
        this.component = component;
        this.direction = direction;
        this.hitCollider = hitCollider;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    @Override
    public Bounds getHitBounds() {
        return hitCollider.getBoundsInParent();
    }

    @Override
    public Rectangle getHitCollider() {
        return hitCollider;
    }

    @Override
    public void update(Town t, GraphicsContext context){
        context.save();
        context.setFill(Color.RED);
        if (enabled){
            double prevX = hitCollider.getX();
            double prevY = hitCollider.getY();
            hitCollider.setX(component.getSpeed()*Math.cos(direction)+prevX);
            hitCollider.setY(component.getSpeed()*Math.sin(direction)+prevY);

            if (!t.square.getBoundsInLocal().intersects(hitCollider.getBoundsInParent())){
                direction = ThreadLocalRandom.current().nextDouble()*360;
                hitCollider.setX(prevX);
                hitCollider.setY(prevY);
            }
            context.setFill(Color.BLUE);
        }
        drawPerson(context,hitCollider);
        context.restore();
    }


    private void drawPerson(GraphicsContext gc,Rectangle rect){
        gc.fillRect(rect.getX(),
                rect.getY(),
                rect.getWidth(),
                rect.getHeight());
    }

}
