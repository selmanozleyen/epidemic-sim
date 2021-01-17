package persons;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public interface IPeople {
    double getMortalityRate();

    double getSpreadFactor();

    void addPersonList(List<IPerson> people);

    void update(ITown t, GraphicsContext context);

    List<IPerson> getAvailablePersons();
}
