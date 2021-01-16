package persons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Town {

    People people = new People();
    HospitalService hospitalService = new HospitalService();
    final int width=1000;
    final int height=600;
    Rectangle square = new Rectangle(width,height);
    public Town(People people){
        this.people = people;
    }



    public void update(GraphicsContext context){
        context.clearRect(0,0,width,height);
        people.update(this,context);
        hospitalService.update(this);
    }
}
