import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;


public class SimController {
    @FXML
    public NumberAxis xAxis;
    @FXML
    public NumberAxis yAxis;
    @FXML
    public AreaChart<Number,Number> areaChart;
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
