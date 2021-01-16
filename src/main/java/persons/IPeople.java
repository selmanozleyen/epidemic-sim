package persons;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public interface IPeople {
    void addPersonList(List<IPerson> people);

    void update(Town t, GraphicsContext context);
}
