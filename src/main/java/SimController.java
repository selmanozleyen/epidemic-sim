import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Button;


public class SimController {
    public StackedAreaChart<Number,Number> areaChart;
    public NumberAxis xAxis;
    public NumberAxis yAxis;
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
