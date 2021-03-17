import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;


public class SimController {
    @FXML
    public NumberAxis xAxis;
    @FXML
    public NumberAxis yAxis;

    public ListView<Double> listView;
    public ListView<String> listViewLabel;


    public LineChart<Number, Number> getAreaChart() {
        return areaChart;
    }

    @FXML
    private LineChart<Number,Number> areaChart;
    @FXML
    private Button pauseBtn;
    @FXML
    private Canvas townCanvas;

    public static final double CANVAS_WIDTH = 1000;
    public static final double CANVAS_HEIGHT = 600;

    public Button getPauseBtn() {
        return pauseBtn;
    }

    public GraphicsContext getTownGraphics() {
        return townCanvas.getGraphicsContext2D();
    }
}
