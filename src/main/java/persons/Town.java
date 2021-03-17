package persons;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;


public class Town implements ITown {

    private final IPeople people;
    private final int width=1000;
    final int height=600;
    private final Rectangle square = new Rectangle(width,height);
    private final HospitalService hospitalService;

    @Override
    public Node getTownSquare() {
        return square;
    }

    @Override
    public HospitalService getHospitalService() {
        return hospitalService;
    }

    public Town(IPeople people){
        this.people = people;
        hospitalService = new HospitalService(people.getAvailablePersons().size());
    }

    @Override
    public void update(GraphicsContext context){
        context.clearRect(0,0,width,height);
        people.update(this,context);
        hospitalService.update(this,people.getAvailablePersons());
    }
}
