package persons;

import java.util.List;

public interface IPersonFactory {
    public List<Person> createPersons(
            double minX, double minY, double maxX, double maxY,
            IPhysicalComponent pc,
            IHealthComponent hc,
            int count);

}
