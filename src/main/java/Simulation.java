import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.util.Duration;
import persons.*;

import java.io.IOException;


public class Simulation {
    private final Thread thread;
    final Task<Void> task;
    Parent root;
    FXMLLoader loader;
    SimController simController;
    Parent getRootPane(){
        return root;
    }
    ITown town;

    Simulation(
            ITown town
    ) throws IOException {
        loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        root = loader.load();
        simController = loader.<SimController>getController();
        this.town = town;

        task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                SimRunnable sim = new SimRunnable(
                        town,
                        simController.getTownGraphics(),
                        simController.getPauseBtn());
                sim.timeline.play();
                return null;
            }
        };
        this.thread = new Thread(task);
    }
    public void start(){
        thread.start();
    }

    private static class SimRunnable {
        boolean active = true;
        ITown town;
        Timeline timeline;
        GraphicsContext gc;
        Button pauseBtn;
        public SimRunnable(ITown town, GraphicsContext gc, Button pauseBtn) {
            this.town = town;
            this.gc = gc;
            this.pauseBtn = pauseBtn;
            this.pauseBtn.setOnAction(
                    (e) -> {
                        if(active){
                            timeline.stop();
                            active = false;
                            this.pauseBtn.setText("Resume");
                        } else{
                            timeline.play();
                            active = true;
                            this.pauseBtn.setText("Pause");
                        }
                    }
            );
            town.getHospitalService().setPauseBtn(pauseBtn);
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(16.6), e ->{
                        town.update(gc);
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(false);
        }
    }
}

