package persons;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;

public interface ITown {
    HospitalService getHospitalService();

    void update(GraphicsContext gc);

    Node getTownSquare();
}
