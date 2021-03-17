import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Callback;
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
                if (controller.getPersonList().isEmpty())
                    return;
                try {
                    IPeople people = new People(controller.getSpreadFactor(), controller.getMortalityRate());
                    List<IPerson> personList = controller.getPersonList();
                    int maskedCount = 0;
                    double socialDistance = 0;
                    for (IPerson p : personList) {
                        if (p.getHealthState().getComponent().getMaskCoefficient() == 0.2)
                            maskedCount++;
                        socialDistance += p.getHealthState().getComponent().getSocialDistance();
                    }
                    personList.get(ThreadLocalRandom.current().nextInt(personList.size()))
                            .getHealthState().setInfected(true);
                    people.addPersonList(personList);
                    ITown town = new Town(people);
                    sim = new Simulation(town);
                    sim.getSimController().getAreaChart().setData(controller.getSeries());
                    controller.getSeries().add(new XYChart.Series<>());
                    controller.getSeries().add(new XYChart.Series<>());
                    controller.getSeries().get(0).getData().add(new XYChart.Data<>(0, 0));
                    controller.getSeries().get(1).getData().add(new XYChart.Data<>(0, 0));
                    controller.getSeries().get(0).setName("Death");
                    controller.getSeries().get(1).setName("Infections");
                    ObservableList<Double> list = FXCollections.observableArrayList(
                            0.0,
                            1.0
                            , 1.0 * personList.size(),
                            controller.getSpreadFactor(),
                            controller.getMortalityRate(),
                            socialDistance / personList.size(),
                            1.0 * maskedCount / personList.size()
                    );
                    ObservableList<String> labelList = FXCollections.observableArrayList(
                            "deathCount",
                            "infectedCount",
                            "initPopulation",
                            "spreadingRate",
                            "mortalityFactor",
                            "avgSocialDistance",
                            "avgMaskUsage"
                    );
                    sim.getSimController().listView.setItems(list);
                    sim.getSimController().listView.setFixedCellSize(-1);
                    sim.getSimController().listViewLabel.setItems(labelList);

                    controller.getSeries().get(0).getData().addListener(
                        (ListChangeListener<XYChart.Data<Number, Number>>)
                                c -> {
                                    while (c.next()) {
                                        if (c.wasAdded()) {
                                            int idx = c.getAddedSubList().size();
                                            list.set(0, c.getAddedSubList().get(idx-1).getYValue().doubleValue());
                                            return;
                                        }
                                    }
                                }
                    );
                    controller.getSeries().get(1).getData().addListener(
                        (ListChangeListener<XYChart.Data<Number, Number>>)
                                c -> {
                                    while (c.next()) {
                                        if (c.wasAdded()) {
                                            int idx = c.getAddedSubList().size();
                                            list.set(1, c.getAddedSubList().get(idx-1).getYValue().doubleValue());
                                            return;
                                        }
                                    }
                                }
                    );
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


