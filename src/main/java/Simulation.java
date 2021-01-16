import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import persons.*;

import java.io.IOException;


public class Simulation {
    Parent root;
    FXMLLoader loader;
    SimController simController;
    Parent getRootPane(){
        return root;
    }
    Thread thread;
    Simulation(ITown town) throws IOException {
        this.loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        this.root = loader.load();
        this.simController = loader.<SimController>getController();
        this.thread  = new Thread(new SimRunnable(town,simController.getTownGraphics(), simController.getPauseBtn()));
    }
    public void start(){
        thread.start();
    }
    private static class SimRunnable implements Runnable{
        public SimRunnable(ITown town, GraphicsContext gc, Button pauseBtn) {
            this.town = town;
            this.gc = gc;
            this.pauseBtn = pauseBtn;
            this.pauseBtn.setOnAction(
                    (e) -> {
                        if(active){
                            this.timer.stop();
                            active = false;
                            Platform.runLater(() -> this.pauseBtn.setText("Resume"));
                        } else{
                            this.timer.start();
                            active = true;
                            Platform.runLater(() -> this.pauseBtn.setText("Pause"));
                        }
                    }
            );
            this.timer = new AnimationTimer(){
                private long lastUpdate = 0 ;
                @Override
                public void handle(long now) {
                    if(now - lastUpdate >= 1_000_000){
                        town.update(gc);
                        lastUpdate = now;
                    }
                }
            };
        }
        boolean active = true;
        ITown town;
        AnimationTimer timer;
        GraphicsContext gc;
        Button pauseBtn;
        /**
         * When an object implementing interface {@code Runnable} is used
         * to create a thread, starting the thread causes the object's
         * {@code run} method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method {@code run} is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            timer.start();
        }
    }
}

