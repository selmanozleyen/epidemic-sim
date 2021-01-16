import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.util.StringConverter;

import java.io.IOException;

public class SimulationLauncher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    LauncherController controller;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sim_launcher.fxml"));
        Parent root = loader.load();
        controller = loader.getController();


        Scene scene = new Scene(root);

        controller.getRunButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Simulation sim = null;
                try {
                    sim = new Simulation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert sim != null;
                primaryStage.setScene(new Scene(sim.getRootPane()));
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
