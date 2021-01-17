import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import persons.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class App extends Application {

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
                    IPeople people = new People(controller.getSpreadFactor(),controller.getMortalityRate());
                    List<IPerson> personList = controller.getPersonList();
                    personList.get(ThreadLocalRandom.current().nextInt(personList.size()))
                            .getHealthState().setInfected(true);
                    people.addPersonList(personList);
                    ITown town = new Town(people);
                    sim = new Simulation(town);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                sim.start();
                primaryStage.setScene(new Scene(sim.getRootPane()));
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
