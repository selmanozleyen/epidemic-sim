import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ListPropertyBase;
import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;
import persons.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;


public class LauncherController implements Initializable {
    @FXML
    private Spinner<Double> mortalitySpinner;
    @FXML
    private Spinner<Double> spreadingSpinner;
    @FXML
    private Button clearButton;
    @FXML
    private ProgressBar progress;
    @FXML
    private ComboBox<String> maskValue;
    @FXML
    private Spinner<Integer> personNoSpinner;

    public LauncherController() throws NoSuchMethodException {
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
    private Button runButton;
    ObservableList<XYChart.Series<Number,Number>> deathSeries = FXCollections.observableArrayList();
    //private final JavaBeanIntegerProperty deathEvent  = JavaBeanIntegerPropertyBuilder.create().name("death").build();
    private final JavaBeanBooleanProperty infectionEvent  = null;
            //JavaBeanBooleanPropertyBuilder.create().name("infection").build();

    JavaBeanBooleanProperty getInfectionEvent(){
        return infectionEvent;
    }

    private final IHealthStateFactory hf = new HealthStateFactory();
    private final IPhysicalStateFactory psf = new PhysicalStateFactory();

    private final IHealthStateFactory shf = new IHealthStateFactory(){
        @Override
        public IHealthState createHealthState(IHealthComponent hc, Circle socialCollider) {
            return new HealthStateWithStats(
                    new HealthStateFactory().createHealthState(hc,socialCollider),
                    deathSeries,infectionEvent
            );
        }
    };
    private final List<IPerson> personList = new ArrayList<>();

    public List<IPerson> getPersonList() {
        return personList;
    }
    public double getSpreadFactor() {
        return spreadingSpinner.getValue();
    }
    public double getMortalityRate(){
        return mortalitySpinner.getValue();
    }

    @FXML
    void clearPersonList(ActionEvent e) {
        personList.clear();
        personNo.setText("Total Count: 0");
        progress.setProgress(personList.size()/2_000.0);
        progress.setAccessibleText("Recommended Limit (2000)");
    }

    @FXML
    void handleAddPerson(ActionEvent e){
        double w = SimController.CANVAS_WIDTH;
        double h =  SimController.CANVAS_HEIGHT;
        double t = 5,deg = 360;
        PersonFactory pf = new PersonFactory(
                new PhysicalComponent(speedSpinner.getValue()),
                new HealthComponent(
                        socialDistanceSpinner.getValue(),
                        socialTimeSpinner.getValue(),
                        Double.parseDouble(maskValue.getValue())
                ), psf,shf
        );
        double x,y,direction;
        for (int i = 0; i < personNoSpinner.getValue(); i++) {
            x = ThreadLocalRandom.current().nextDouble()*(w-t)+t;
            y = ThreadLocalRandom.current().nextDouble()*(h-t)+t;
            direction = ThreadLocalRandom.current().nextDouble()*deg;
            personList.add(pf.createPerson(x,y,direction));
        }
        personNo.setText("Total Count: "+personList.size());
        progress.setProgress(personList.size()/2_000.0);
        progress.setAccessibleText("Recommended Limit (2000)");
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
        Alert alert = new Alert(Alert.AlertType.WARNING,"invalid value",ButtonType.APPLY);
        personNoSpinner.getValueFactory().setConverter(
                new IgnoringIntegerConverter(alert,1)
        );
        socialTimeSpinner.getValueFactory().setConverter(
                new IgnoringDoubleConverter(alert,1.0)
        );
        socialDistanceSpinner.getValueFactory().setConverter(
                new IgnoringDoubleConverter(alert,1.0)
        );
        speedSpinner.getValueFactory().setConverter(
                new IgnoringDoubleConverter(alert,1.0)
        );
        mortalitySpinner.getValueFactory().setConverter(
                new IgnoringDoubleConverter(alert,0.2)
        );
        // Some hack to fix a os dependent bug (ENG and TR representations of doubles)
        mortalitySpinner.getValueFactory().valueProperty().setValue(0.2);

        spreadingSpinner.getValueFactory().setConverter(
                new IgnoringDoubleConverter(alert,0.6)
        );
        // Some hack to fix a os dependent bug (ENG and TR representations of doubles)
        spreadingSpinner.getValueFactory().valueProperty().setValue(0.6);

    }
    private static class IgnoringIntegerConverter extends StringConverter<Integer> {
        private final Alert a;
        private final int defaultValue;
        IgnoringIntegerConverter(Alert a, Integer defaultValue) {this.a = a;this.defaultValue = defaultValue;}
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
            int res;
            try{
                res = Integer.parseInt(string);
            }catch(Exception e){
                a.show();
                res = defaultValue;
            }
            return res;
        }
    }
    private static class IgnoringDoubleConverter extends StringConverter<Double> {
        Alert a;
        private final double defaultValue;
        IgnoringDoubleConverter(Alert a,Double defaultValue) {
            this.a = a;
            this.defaultValue = defaultValue;
        }
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
            double res;
            try{
                res = Double.parseDouble(string);
            }catch(Exception e){
                a.show();
                res = defaultValue;
            }
            return res;
        }
    }
}
