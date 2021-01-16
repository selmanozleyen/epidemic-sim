package persons;

import java.util.List;

public interface IPersonFactory {
    Person createPerson(double x, double y, double direction);
    public List<Person> createPersons(
            double minX, double minY, double maxX, double maxY, int count);

}
