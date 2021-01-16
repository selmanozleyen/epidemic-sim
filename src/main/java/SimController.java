import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;


public class SimController {
    @FXML
    private Button pauseBtn;
    @FXML
    private Canvas townCanvas;

    public Button getPauseBtn() {
        return pauseBtn;
    }

    public GraphicsContext getTownGraphics() {
        return townCanvas.getGraphicsContext2D();
    }
}
