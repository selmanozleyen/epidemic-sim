import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.util.Duration;
import persons.*;

import java.io.IOException;


public class Simulation {
    private final SimRunnable sim;
    Parent root;
    FXMLLoader loader;
    SimController simController;
    Parent getRootPane(){
        return root;
    }
    ITown town;

    Simulation(ITown town) throws IOException {
        this.loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        this.root = loader.load();
        this.simController = loader.<SimController>getController();
        this.town = town;
        this.sim = new SimRunnable(town,simController.getTownGraphics(), simController.getPauseBtn());
        /*Task<Void> task =  new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                (new SimRunnable(town,simController.getTownGraphics(), simController.getPauseBtn())).run();
                return null;
            }
        };
        this.thread  = new Thread(task);*/
    }
    public void start(){
        sim.run();
        //thread.start();
    }
    private static class SimRunnable {
        boolean active = true;
        ITown town;
        Timeline timeline;
        AnimationTimer timer;
        GraphicsContext gc;
        Button pauseBtn;
        public SimRunnable(ITown town, GraphicsContext gc, Button pauseBtn) {
            this.town = town;
            this.gc = gc;
            this.pauseBtn = pauseBtn;
            this.pauseBtn.setOnAction(
                    (e) -> {
                        if(active){
                            this.timeline.stop();
                            active = false;
                            this.pauseBtn.setText("Resume");
                        } else{
                            this.timeline.play();
                            active = true;
                            this.pauseBtn.setText("Pause");
                        }
                    }
            );
            this.timeline = new Timeline(
                    new KeyFrame(Duration.millis(16), e ->{
                        town.update(gc);
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(false);
            //timeline.set
            //timeline.setCycleCount(Duration.INDEFINITE);
            /*this.timer = new AnimationTimer(){
                @Override
                public void handle(long now) {
                    timeline.play();
                }
            };*/
        }

        public void run() {
            timeline.play();
        }
    }
}

