package persons;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    /*public List<IPerson> createPersons(
            double minX,
            double minY,
            double maxX,
            double maxY,
            int count
    ){
        assert count > 0 && minX < maxX && minY < maxY && pc != null && hc != null;

        ArrayList<IPerson> res = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double x = ThreadLocalRandom.current().nextDouble()*(maxX - minX)+minX;
            double y = ThreadLocalRandom.current().nextDouble()*(maxY - minY)+minY;
            double direction = ThreadLocalRandom.current().nextDouble()*360;
            PhysicalState ps = new PhysicalState(pc,new Rectangle(x,y,
                    pc.getThickness(),pc.getThickness()),direction);

            HealthState hs = new HealthState(
                    hc,
                    new Circle(x,y,
                            pc.getThickness()*1.414+hc.getSocialDistance()));
            res.add(new Person(ps,hs));
        }
        return res;
    }*/
}
