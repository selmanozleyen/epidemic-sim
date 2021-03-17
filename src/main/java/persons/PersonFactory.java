package persons;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class PersonFactory implements IPersonFactory{
    private final IHealthComponent hc;
    private final IPhysicalComponent pc;
    private final IHealthStateFactory hf;
    private final IPhysicalStateFactory pf;

    public PersonFactory(IPhysicalComponent pc, IHealthComponent hc,
                         IPhysicalStateFactory pf, IHealthStateFactory hf){
        this.pc = pc;this.hc = hc;this.pf =pf;this.hf = hf;
    }
    public Person createPerson(double x, double y, double direction){
        IPhysicalState ps = pf.createPhysicalState(
                pc,
                new Rectangle(x,y, pc.getThickness(),pc.getThickness()),
                direction
        );
        IHealthState hs = hf.createHealthState(hc,
                new Circle(x+pc.getThickness()/2.0,y+pc.getThickness()/2.0,
                pc.getThickness()+hc.getSocialDistance()));
        return new Person(ps,hs);
    }
}
