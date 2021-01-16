import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import persons.IPeople;
import persons.ITown;
import persons.People;
import persons.Town;

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
                    IPeople people = new People();
                    people.addPersonList(controller.getPersonList());
                    ITown town = new Town(people,controller.getSpreadFactor(),controller.getMortalityRate());
                    sim = new Simulation(town);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                primaryStage.setScene(new Scene(sim.getRootPane()));
                sim.start();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
