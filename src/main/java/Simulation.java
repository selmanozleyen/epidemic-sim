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
    }
    public void start(){
        sim.run();
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
                    new KeyFrame(Duration.millis(16.6), e ->{
                        town.update(gc);
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(false);
        }

        public void run() {
            timeline.play();
        }
    }
}

