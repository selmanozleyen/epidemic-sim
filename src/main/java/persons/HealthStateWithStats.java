package persons;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.XYChart;


public class HealthStateWithStats implements IHealthState{

    private final IHealthState inner;
    private final long start;
    private final ObservableList<XYChart.Series<Number, Number>> series;
    private boolean prevDead=false;
    private boolean prevInfected=false;
    public HealthStateWithStats(IHealthState inner,
                                ObservableList<XYChart.Series<Number,Number>> series

    ) {
        this.inner = inner;
        this.series = series;
        this.start = System.currentTimeMillis();
    }

    private void incrementOccurrence(int i){
        XYChart.Data<Number, Number> prev = series.get(i).getData().get(series.get(i).getData().size() - 1);
        XYChart.Data<Number, Number> e = new XYChart.Data<>(System.currentTimeMillis()-start,
                prev.getYValue().intValue()+1);
        series.get(i).getData().add(e);
    }

    @Override
    public double getTimeToDie() {
        return inner.getTimeToDie();
    }

    @Override
    public void setTimeToDie(double timeToDie) {
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
        if(!prevInfected && inner.isInfected()){
            incrementOccurrence(1);
        }
        prevInfected = inner.isInfected();
        return inner.isInfected();
    }

    @Override
    public void setInfected(boolean infected) {
        inner.setInfected(infected);
    }

    @Override
    public boolean isDead() {
        if (!prevDead && inner.isDead()) {
            incrementOccurrence(0);
        }
        prevDead = inner.isDead();
        return prevDead;
    }
}
