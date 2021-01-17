package persons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Town implements ITown {

    IPeople people;
    HospitalService hospitalService = new HospitalService();
    final int width=1000;
    final int height=600;

    Rectangle square = new Rectangle(width,height);
    public Town(IPeople people){
        this.people = people;
    }

    @Override
    public void update(GraphicsContext context){
        context.clearRect(0,0,width,height);
        context.beginPath();
        people.update(this,context);
        hospitalService.update(this);
    }
}
