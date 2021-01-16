package persons;

import java.util.List;

public interface IPersonFactory {
    IPerson createPerson(double x, double y, double direction);
    /*public List<IPerson> createPersons(
            double minX, double minY, double maxX, double maxY, int count);*/

}
