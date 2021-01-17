package persons;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class PersonFactory implements IPersonFactory{
    private final IHealthComponent hc;
    private final IPhysicalComponent pc;
    public PersonFactory(IPhysicalComponent pc, IHealthComponent hc){
        this.pc = pc;this.hc = hc;
    }
    public Person createPerson(double x, double y, double direction){
        PhysicalState ps = new PhysicalState(
                pc,
                new Rectangle(x,y, pc.getThickness(),pc.getThickness()),
                direction
        );
        HealthState hs = new HealthState(hc,
                new Circle(x+pc.getThickness()/2.0,y+pc.getThickness()/2.0,
                pc.getThickness()+hc.getSocialDistance()));
        return new Person(ps,hs);
    }
}
