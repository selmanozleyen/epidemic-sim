package persons;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanProperty;
import javafx.beans.value.*;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.event.EventTarget;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;

import java.util.function.Consumer;
import java.util.function.Function;

public class HealthStateWithStats implements IHealthState{

    private final IHealthState inner;
    private final JavaBeanBooleanProperty infectionEvent;
    private final ObservableList<XYChart.Series<Number, Number>> deathEvent;
    private boolean prevDead=false;

    public HealthStateWithStats(IHealthState inner,
                                ObservableList<XYChart.Series<Number,Number>> deathEvent,
                                JavaBeanBooleanProperty infectionEvent
    ) {
        this.inner = inner;

        this.infectionEvent = infectionEvent;

        this.deathEvent = deathEvent;
    }

    @Override
    public double getTimeToDie() {
        return inner.getTimeToDie();
    }

    @Override
    public void setTimeToDie(double timeToDie) {
        if(getTimeToDie() >= 0 && timeToDie <= 0){
            deathEvent.get(deathEvent.size()-1).getData().add(new XYChart.Data<Number,Number>(1,1));
            System.out.println(deathEvent);
        }
        inner.setTimeToDie(timeToDie);
    }

    @Override
    public double getTimeToHospital() {
        return inner.getTimeToHospital();
    }

    @Override
    public void setTimeToHospital(double timeToHospital) {
        inner.setTimeToHospital(timeToHospital);
    }

    @Override
    public IHealthComponent getComponent() {
        return inner.getComponent();
    }

    @Override
    public Bounds getSocialBounds() {
        return inner.getSocialBounds();
    }

    @Override
    public void update(ITown t, GraphicsContext context, double x, double y) {
        inner.update(t,context,x,y);
    }

    @Override
    public boolean isInfected() {
        return inner.isInfected();
    }

    @Override
    public void setInfected(boolean infected) {
        //infectionEvent.fireValueChangedEvent();
        inner.setInfected(infected);
    }

    @Override
    public boolean isDead() {
        if (!prevDead && inner.isDead()) {
            deathEvent.get(0).getData().add(
                    new XYChart.Data<>(deathEvent.get(0).getData())
                    //deathEvent.get(0).getData().get(deathEvent.get(deathEvent.size()-1);

                    .getnew XYChart.Data<Number,Number>(1,1));
            System.out.println(deathEvent);
        }
        prevDead = inner.isDead();
        return prevDead;
    }
}
