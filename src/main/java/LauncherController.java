import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import persons.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;


public class LauncherController implements Initializable {
    @FXML
    private ProgressBar progress;
    @FXML
    private ComboBox<String> maskValue;
    @FXML
    private Spinner<Integer> personNoSpinner;

    public Spinner<Double> getSocialTimeSpinner() {
        return socialTimeSpinner;
    }

    public Spinner<Double> getSocialDistanceSpinner() {
        return socialDistanceSpinner;
    }

    public Spinner<Double> getSpeedSpinner() {
        return speedSpinner;
    }

    @FXML
    private Spinner<Double> socialTimeSpinner;
    @FXML
    private Spinner<Double> socialDistanceSpinner;
    @FXML
    private Spinner<Double> speedSpinner;
    @FXML
    private Label personNo;
    @FXML
    private Button addPerson;
    @FXML
    private Button runButton;

    List<Person> personList = new ArrayList<>();

    @FXML
    void handleAddPerson(ActionEvent e){
        PersonFactory pf = new PersonFactory(
                new PhysicalComponent(speedSpinner.getValue()),
                new HealthComponent(
                        socialDistanceSpinner.getValue(),
                        socialTimeSpinner.getValue(),
                        Double.parseDouble(maskValue.getValue())
                )
        );
        double x,y,direction;
        for (int i = 0; i < personNoSpinner.getValue(); i++) {
            //TODO make const var
            x = ThreadLocalRandom.current().nextDouble()*1000;
            y = ThreadLocalRandom.current().nextDouble()*600;
            direction = ThreadLocalRandom.current().nextDouble()*360;
            personList.add(pf.createPerson(x,y,direction));
        }
        personNo.setText("Total Count: "+personList.size());
        progress.setProgress(personList.size()/1_000.0);
    }

    public Button getRunButton() {
        return runButton;
    }

    public Spinner<Integer> getPersonNoSpinner() {
        return personNoSpinner;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        personNoSpinner.getValueFactory().setConverter(
                new IgnoringIntegerConverter()
        );
        socialTimeSpinner.getValueFactory().setConverter(
                new IgnoringDoubleConverter()
        );
        socialDistanceSpinner.getValueFactory().setConverter(
                new IgnoringDoubleConverter()
        );
        speedSpinner.getValueFactory().setConverter(
                new IgnoringDoubleConverter()
        );
    }

    private static class IgnoringIntegerConverter extends StringConverter<Integer> {

        /**
         * Converts the object provided into its string form.
         * Format of the returned string is defined by the specific converter.
         *
         * @param object the object of type {@code T} to convert
         * @return a string representation of the object passed in.
         */
        @Override
        public String toString(Integer object) {
            return object.toString();
        }

        /**
         * Converts the string provided into an object defined by the specific converter.
         * Format of the string and type of the resulting object is defined by the specific converter.
         *
         * @param string the {@code String} to convert
         * @return an object representation of the string passed in.
         */
        @Override
        public Integer fromString(String string) {
            int res = 0;
            try{
                res = Integer.parseInt(string);
            }catch(Exception e){
                res = 0;
            }
            return res;
        }
    }
    private static class IgnoringDoubleConverter extends StringConverter<Double> {

        /**
         * Converts the object provided into its string form.
         * Format of the returned string is defined by the specific converter.
         *
         * @param object the object of type {@code T} to convert
         * @return a string representation of the object passed in.
         */
        @Override
        public String toString(Double object) {
            return object.toString();
        }

        /**
         * Converts the string provided into an object defined by the specific converter.
         * Format of the string and type of the resulting object is defined by the specific converter.
         *
         * @param string the {@code String} to convert
         * @return an object representation of the string passed in.
         */
        @Override
        public Double fromString(String string) {
            double res = 0;
            try{
                res = Double.parseDouble(string);
            }catch(Exception e){
                res = 0;
            }
            return res;
        }
    }
}
