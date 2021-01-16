package persons;

import javafx.geometry.Bounds;

public interface IHealthState {
    IHealthComponent getComponent();

    Bounds getSocialBounds();
}
