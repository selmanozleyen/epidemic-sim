import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import persons.*;

import java.io.IOException;


public class Simulation {
    Parent root;
    FXMLLoader loader;
    AppController townController;
    Parent getRootPane(){
        return root;
    }

    Simulation() throws IOException {
        this.loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        this.root = loader.load();
        this.townController = loader.<AppController>getController();

    }

    void temp(){
        //HealthComponent hc = new HealthComponent(5,12,0.2f);
        /*HealthState hs = new HealthState(new Circle(10));
        PhysicalComponent pc = new PhysicalComponent(12,12,2.1,2,270);

        Person p = new Person(pc,hc,hs);

        People people = new People();
        people.addPerson(p);
        Town town = new Town(people);

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                town.update(townController.getTownGraphics());
            }
        };
        timer.start();*/
    }
}